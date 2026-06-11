package tpObligatorio;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class TransporteATerminal {
    private CyclicBarrier barrera;
    private Runnable accionInicio;
    private final Semaphore mutex;
    private final Semaphore maximoPasajeros;
    private final Semaphore[] barreraTerminal; // sincroniza salida por terminal
    private final int cantTerminales;
    private int[] pasajerosABordo; // cantidad por terminal
    private final AtomicInteger totalSubidos = new AtomicInteger(0); // contador total de pasajeros subidos (para debug)
    private final int totalPasajeros, capacidad; // cantidad total de pasajeros que se espera transportar (para debug)

    public TransporteATerminal(int cantidad, int cantidadTerminales, int totalPasajeros) {
        this.capacidad = cantidad;
        this.accionInicio = avisarConductor();
        this.barrera = new CyclicBarrier(cantidad, accionInicio);
        this.mutex = new Semaphore(1, true);
        this.maximoPasajeros = new Semaphore(cantidad, true);
        this.cantTerminales = cantidadTerminales;
        this.totalPasajeros = totalPasajeros;
        this.barreraTerminal = new Semaphore[cantidadTerminales];
        this.pasajerosABordo = new int[cantidadTerminales];
        for (int i = 0; i < cantidadTerminales; i++) {
            this.barreraTerminal[i] = new Semaphore(0, true);
            this.pasajerosABordo[i] = 0;
        }
    }

    // Pasajero sube al transporte
    public void subirATransporte(int numeroTerminal) throws InterruptedException {
        if (numeroTerminal < 1 || numeroTerminal > cantTerminales) {
            throw new IllegalArgumentException("Terminal invalida: " + numeroTerminal);
        }

        // Registra que va a esta terminal
        System.out.println(Thread.currentThread().getName() + " intenta subir al transporte");
        maximoPasajeros.acquire();

        // El pasajero avisa que va a esta terminal (semáforo destino)
        mutex.acquire();
        this.pasajerosABordo[numeroTerminal - 1]++;
        System.out.println(Thread.currentThread().getName() + " sube al transporte");
        int subidos = totalSubidos.incrementAndGet();
        mutex.release();

        // Espera a que el transporte arranque (todos los cupos llenos)
        try {
            barrera.await();
        } catch (BrokenBarrierException e) {
            // Si la barrera se rompe, liberamos los rescursos (devuelve el cupo que
            // tomamos) y relanzamos la excepcion

            // maximoPasajeros.release();
            // mutex.acquire();
            // this.pasajerosABordo[numeroTerminal - 1]--;
            // mutex.release();
            // throw new InterruptedException("Barrera rota al subir: " + e.getMessage());

            // Si la barrera se rompe (por el hilo de cierre), el viaje igual continúa
            System.out.println(Thread.currentThread().getName() + " viaje forzado por fin de pasajeros");
        }

        // Cuando todos los pasajeros han subido y no se completó el último grupo,
        // un hilo aparte rompe la barrera.
        if (subidos == totalPasajeros && subidos % capacidad != 0) {
            new Thread(() -> {
                try {
                    Thread.sleep(100); // espera breve para que todos lleguen a await()
                    barrera.reset(); // fuerza la ruptura en los que están esperando
                } catch (InterruptedException ex) {
                }
            }).start();
        }

    }

    // Pasajero baja cuando el conductor libera su terminal
    public void bajarDelTransporte(int numeroTerminal) throws InterruptedException {
        if (numeroTerminal < 1 || numeroTerminal > cantTerminales) {
            throw new IllegalArgumentException("Terminal invalida: " + numeroTerminal);
        }

        // Espera a que el conductor confirme la parada de esta terminal (release de
        // barreraTerminal)
        barreraTerminal[numeroTerminal - 1].acquire();
        System.out.println(Thread.currentThread().getName() + " baja del transporte en la terminal "
                + cadenaTerminal(numeroTerminal));

        // Actualiza contadores y libera cupo FUERA de mutex cuando es posible
        mutex.acquire();
        try {
            this.pasajerosABordo[numeroTerminal - 1]--;
        } finally {
            mutex.release();
        }
        maximoPasajeros.release();
    }

    // Conductor confirma parada: libera a todos los pasajeros de esa terminal
    public void confirmarParada(int parada) throws InterruptedException {
        if (parada < 1 || parada > cantTerminales) {
            throw new IllegalArgumentException("Parada invalida: " + parada);
        }
        int n;
        mutex.acquire();
        try {
            n = this.pasajerosABordo[parada - 1];
        } finally {
            mutex.release();
        }
        if (n > 0) {
            barreraTerminal[parada - 1].release(n);
        }
    }

    // Prepara el transporte para un nuevo recorrido
    public void reiniciarRecorrido() throws InterruptedException {
        mutex.acquire();
        try {
            // Resetea la barrera (hilos en await reciben BrokenBarrierException)
            barrera.reset();
            for (int i = 0; i < this.cantTerminales; i++) {
                this.pasajerosABordo[i] = 0;
                // Drena los permisos de la corrida anterior
                this.barreraTerminal[i].drainPermits();
            }
        } finally {
            mutex.release();
        }
    }

    private Runnable avisarConductor() {
        return new Conductor(this, this.cantTerminales);
    }

    private String cadenaTerminal(int numeroTerminal) {
        if (numeroTerminal < 1 || numeroTerminal > 26) {
            return "?";
        }
        return Character.toString(('A' + numeroTerminal - 1));
    }
}