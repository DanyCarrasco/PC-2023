package tpObligatorio;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class TransporteATerminal {
    private CyclicBarrier barrera;
    private final Semaphore mutex;
    private final Semaphore maximoPasajeros;
    private final Semaphore[] barreraTerminal;
    private final int cantTerminales;
    private int[] pasajerosABordo;
    private int totalSubidos;
    private final int capacidad;
    private final CountDownLatch finPasajeros;
    private int pasajerosTerminal[];
    private final Semaphore puedeAbordar;

    public TransporteATerminal(int cantidad, int cantidadTerminales, int totalPasajeros) {
        this.capacidad = cantidad;
        this.barrera = new CyclicBarrier(cantidad, this::avisarConductor);
        this.mutex = new Semaphore(1, true);
        this.maximoPasajeros = new Semaphore(cantidad, true);
        this.cantTerminales = cantidadTerminales;
        this.barreraTerminal = new Semaphore[cantidadTerminales];
        this.pasajerosABordo = new int[cantidadTerminales];
        this.pasajerosTerminal = new int[cantidadTerminales];
        for (int i = 0; i < cantidadTerminales; i++) {
            this.barreraTerminal[i] = new Semaphore(0, true);
            this.pasajerosABordo[i] = 0;
            this.pasajerosTerminal[i] = 0;
        }
        this.finPasajeros = new CountDownLatch(totalPasajeros);
        this.puedeAbordar = new Semaphore(cantidad, true);
        this.totalSubidos = 0;
    }

    // Callback de CyclicBarrier: solo se ejecuta cuando el transporte se LLENA
    // completamente (capacidad exacta). El último viaje parcial lo maneja
    // directamente el pasajero que detecta que no hay más gente.
    private void avisarConductor() {
        puedeAbordar.drainPermits();
        Thread conductor = new Thread(new Conductor(this, cantTerminales), "Conductor");
        conductor.start();
    }

    // Pasajero sube al transporte
    public void subirATransporte(int numeroTerminal) throws InterruptedException {
        if (numeroTerminal < 1 || numeroTerminal > cantTerminales) {
            throw new IllegalArgumentException("Terminal invalida: " + numeroTerminal);
        }

        System.out.println(Thread.currentThread().getName() + " espera para abordar el transporte");
        puedeAbordar.acquire();
        System.out.println(Thread.currentThread().getName() + " intenta subir al transporte");
        maximoPasajeros.acquire();

        mutex.acquire();
        this.pasajerosABordo[numeroTerminal - 1]++;
        totalSubidos++;
        System.out.println(Thread.currentThread().getName() + " sube al transporte (" + totalSubidos + " subidos)");
        mutex.release();

        finPasajeros.countDown();

        // Detectar si es el ultimo viaje con carga parcial:
        // 1) los subidos en este viaje no completan la capacidad
        // 2) no quedan mas pasajeros por subir al transporte
        if (totalSubidos % capacidad != 0 && finPasajeros.getCount() == 0) {
            // SOLO el ultimo pasajero ejecuta esto (porque totalSubidos es mutex-protected)
            System.out.println(Thread.currentThread().getName()
                    + " detecto ultimo viaje con carga parcial, arranca conductor");

            // Arrancar el conductor directamente
            Thread conductor = new Thread(new Conductor(this, cantTerminales), "Conductor Parcial");
            conductor.start();

            // Dar tiempo a los pasajeros que estan en barrera.await() para que
            // obtengan BrokenBarrierException, y despues resetear la barrera
            // para el proximo ciclo.
            int pasajerosParciales = totalSubidos % capacidad;
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    barrera.reset();
                    // Los pasajeros parciales que estaban en await() reciben
                    // BrokenBarrierException y salen del metodo.
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        try {
            barrera.await();
        } catch (BrokenBarrierException e) {
            // Viaje parcial: la barrera fue reseteada, el pasajero ya no espera.
            // Simplemente continua y luego baja en su terminal.
        }
    }

    // Pasajero baja del transporte en la terminal indicada
    public void bajarDelTransporte(int numeroTerminal) throws InterruptedException {
        if (numeroTerminal < 1 || numeroTerminal > cantTerminales) {
            throw new IllegalArgumentException("Terminal invalida: " + numeroTerminal);
        }

        barreraTerminal[numeroTerminal - 1].acquire();
        System.out.println(Thread.currentThread().getName() + " baja del transporte en la terminal "
                + cadenaTerminal(numeroTerminal));

        mutex.acquire();
        try {
            this.pasajerosABordo[numeroTerminal - 1]--;
            this.pasajerosTerminal[numeroTerminal - 1]++;
        } finally {
            mutex.release();
        }
        maximoPasajeros.release();
    }

    // Lo realiza Conductor: en cada parada libera a los pasajeros de esa terminal
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

    // Lo realiza Conductor: indica que termino el recorrido y libera el transporte
    // para el proximo viaje
    public void terminoRecorrido() {
        System.out.println("Conductor termino recorrido, liberando transporte para proximo viaje");
        puedeAbordar.release(capacidad);
    }

    private String cadenaTerminal(int numeroTerminal) {
        if (numeroTerminal < 1 || numeroTerminal > 26) {
            return "?";
        }
        return Character.toString((char) ('A' + numeroTerminal - 1));
    }
}
