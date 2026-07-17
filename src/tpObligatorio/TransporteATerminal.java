package tpObligatorio;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class TransporteATerminal {
    private CyclicBarrier barrera;
    private final Semaphore mutex;
    private final Semaphore maximoPasajeros;
    private final Semaphore[] barreraTerminal;
    private final Semaphore inicioUltimoViaje = new Semaphore(0);
    private final int cantTerminales;
    private int[] pasajerosABordo;
    private final AtomicInteger totalSubidos = new AtomicInteger(0);
    private final int capacidad;
    private final CountDownLatch finPasajeros;

    private int pasajerosTerminal[];
    private boolean realizoRecorrido;
    private Semaphore mutexRecorrido;
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

        this.realizoRecorrido = false;
        this.mutexRecorrido = new Semaphore(1);
        this.puedeAbordar = new Semaphore(cantidad, true);
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
        System.out.println(Thread.currentThread().getName() + " sube al transporte");
        int subidos = totalSubidos.incrementAndGet();
        mutex.release();

        finPasajeros.countDown();
        if (subidos % capacidad != 0 && finPasajeros.getCount() == 0) {
            avisarConductor();
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    barrera.reset();
                    inicioUltimoViaje.release(subidos % capacidad);
                } catch (InterruptedException ex) {
                }
            }).start();
        }

        try {
            barrera.await();
        } catch (BrokenBarrierException e) {
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
            this.pasajerosTerminal[numeroTerminal-1]++;
        } finally {
            mutex.release();
        }
        maximoPasajeros.release();
    }

    // Lo realiza Chofer del transporte
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

    // lo usa chofer, indicando que realizo un recorrido
    public void terminoRecorrido(){
        puedeAbordar.release(capacidad);
        try {
            mutexRecorrido.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        realizoRecorrido = true;
        mutexRecorrido.release();
    }

    // Lo usa Terminal para saber la cantidad de pasajeros que bajan en una terminal
    public int getCantidadPasajerosTerminal(int numeroTerminal){
        int cantidadPasajeros = 0;
        try {
            mutexRecorrido.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (realizoRecorrido) {
            cantidadPasajeros = this.pasajerosTerminal[numeroTerminal-1];
        }
        mutexRecorrido.release();
        return cantidadPasajeros;
    }

    private synchronized void avisarConductor() {
        puedeAbordar.drainPermits();
        Thread conductor = new Thread(new Conductor(this, cantTerminales), "Conductor");
        conductor.start();
    }

    private String cadenaTerminal(int numeroTerminal) {
        if (numeroTerminal < 1 || numeroTerminal > 26) {
            return "?";
        }
        return Character.toString((char) ('A' + numeroTerminal - 1));
    }
}
