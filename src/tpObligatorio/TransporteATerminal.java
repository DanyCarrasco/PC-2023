package tpObligatorio;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class TransporteATerminal {
    private CyclicBarrier barrera;
    private final Semaphore mutex;
    private final Semaphore maximoPasajeros;
    private final Semaphore[] barreraTerminal;
    private final Semaphore inicioUltimoViaje = new Semaphore(0);
    private final int cantTerminales;
    private int[] pasajerosABordo;
    private int totalSubidos;
    private final int capacidad;
    private final CountDownLatch finPasajeros;
    private int pasajerosTerminal[];
    private final Semaphore puedeAbordar;
    private final AtomicBoolean conductorSpawning = new AtomicBoolean(false);
    private volatile boolean aeropuertoCerrado = false;
    private Thread monitorAeropuerto;

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
        this.monitorAeropuerto = null;
    }

    private void avisarConductor() {
        if (conductorSpawning.compareAndSet(false, true)) {
            System.out.println("Barrera completa, arrancando conductor");
            puedeAbordar.drainPermits();
            Thread conductor = new Thread(new Conductor(this, cantTerminales), "Conductor");
            conductor.start();
        }
    }

    public void notificarAeropuertoCerrado() {
        this.aeropuertoCerrado = true;
        // Arrancar thread monitor que rompa la barrera si hay pasajeros esperando
        if (monitorAeropuerto == null || !monitorAeropuerto.isAlive()) {
            monitorAeropuerto = new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                System.out.println("Monitor: aeropuerto cerrado, reseteando barrera");
                barrera.reset();
            }, "Monitor Aeropuerto Cerrado");
            monitorAeropuerto.start();
        }
    }

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
        int subidosSnapshot = totalSubidos;
        System.out.println(Thread.currentThread().getName()
                + " sube al transporte (" + subidosSnapshot + " subidos)");
        mutex.release();

        finPasajeros.countDown();

        try {
            barrera.await();
        } catch (BrokenBarrierException e) {
            // La barrera fue reseteada (por monitor de cierre o viaje parcial).
            // Verificar si es viaje parcial y arrancar conductor si hace falta.
            if (subidosSnapshot % capacidad != 0
                    && (finPasajeros.getCount() == 0 || aeropuertoCerrado)) {
                if (conductorSpawning.compareAndSet(false, true)) {
                    System.out.println(Thread.currentThread().getName()
                            + " detecto viaje parcial (" + subidosSnapshot
                            + "/" + capacidad + "), arranca conductor");
                    Thread conductor = new Thread(
                            new Conductor(this, cantTerminales), "Conductor Parcial");
                    conductor.start();
                }
            }
            // Esperar a que el conductor termine y libere el transporte
            System.out.println(Thread.currentThread().getName()
                    + " barrera rota, esperando turno para bajar");
            inicioUltimoViaje.acquire();
        }
    }

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

    public void terminoRecorrido() {
        System.out.println("Conductor termino recorrido, liberando transporte para proximo viaje");
        conductorSpawning.set(false);
        puedeAbordar.release(capacidad);
        inicioUltimoViaje.release(capacidad);
    }

    private String cadenaTerminal(int numeroTerminal) {
        if (numeroTerminal < 1 || numeroTerminal > 26) {
            return "?";
        }
        return Character.toString((char) ('A' + numeroTerminal - 1));
    }
}
