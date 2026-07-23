package tpObligatorio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class TransporteATerminal {
    private CyclicBarrier barrera;
    private ArrayBlockingQueue<Boolean> asientos;

    private Semaphore mutex;
    private Semaphore mutexArranque;
    private Semaphore mutexAeropuerto;
    private Semaphore[] barreraTerminal;
    private Semaphore inicioUltimoViaje = new Semaphore(0);

    private int cantTerminales;
    private int[] pasajerosABordo;
    private int totalSubidos;
    private int capacidad;
    private CountDownLatch finPasajeros;
    private int pasajerosTerminal[];
    private Semaphore puedeAbordar;
    private boolean conductorArrancado = false;
    private boolean aeropuertoCerrado = false;

    public TransporteATerminal(int cantidad, int cantidadTerminales, int totalPasajeros) {
        this.capacidad = cantidad;
        this.barrera = new CyclicBarrier(cantidad, this::avisarConductor);
        this.mutex = new Semaphore(1, true);
        this.mutexArranque = new Semaphore(1, true);
        this.mutexAeropuerto = new Semaphore(1, true);

        this.asientos = new ArrayBlockingQueue<>(cantidad, true);
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

    public void notificarAeropuertoCerrado() {
        try {
            mutexAeropuerto.acquire();
            aeropuertoCerrado = true;
            barrera.reset(); // Rompe la barrera para que los pasajeros que ya subieron puedan bajar
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            mutexAeropuerto.release();
        }
    }

    public void subirATransporte(int numeroTerminal) throws InterruptedException {
        if (numeroTerminal < 1 || numeroTerminal > cantTerminales) {
            throw new IllegalArgumentException("Terminal invalida: " + numeroTerminal);
        }

        System.out.println(Thread.currentThread().getName() + " espera para abordar el transporte");
        puedeAbordar.acquire();
        System.out.println(Thread.currentThread().getName() + " intenta subir al transporte");
        asientos.put(true);

        mutex.acquire();
        this.pasajerosABordo[numeroTerminal - 1]++;
        totalSubidos++;
        int subidosAhora = totalSubidos;
        System.out.println(Thread.currentThread().getName()
                + " sube al transporte (" + subidosAhora + " subidos)");
        mutex.release();

        finPasajeros.countDown();

        try {
            barrera.await();
        } catch (BrokenBarrierException e) {
            // La barrera fue reseteada (por monitor de cierre o viaje parcial).
            // Verificar si es viaje parcial y arrancar conductor si hace falta.
            if (subidosAhora % capacidad != 0
                    && (finPasajeros.getCount() == 0 || aeropuertoCerrado)) {
                if (intentarConductor()) {
                    System.out.println(Thread.currentThread().getName()
                            + " detecto viaje parcial (" + subidosAhora
                            + "/" + capacidad + "), arranca conductor");
                    Thread conductor = new Thread(
                            new Conductor(this, cantTerminales), "Conductor Parcial");
                    conductor.start();
                }
            }
            // Espera a que el conductor termine y libere el transporte
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
        asientos.poll();
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
        try {
            mutexArranque.acquire();
            conductorArrancado = false;
        } catch (InterruptedException e) {
            // TODO: handle exception
        } finally {
            mutexArranque.release();
            puedeAbordar.release(capacidad);
            inicioUltimoViaje.release(capacidad);
        }
    }

    private boolean intentarConductor() {
        boolean intento = false;
        try {
            mutexArranque.acquire();
            if (!conductorArrancado) {
                conductorArrancado = true;
                intento = true;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutexArranque.release();
        }
        return intento;
    }

    private void avisarConductor() {
        // Si el aeropuerto esta cerrado, no arrancar conductor, porque ya hay uno en proceso
        if (intentarConductor()) {
            puedeAbordar.drainPermits(); // Bloquea abordaje de nuevos pasajeros hasta que el conductor termine
            Thread conductor = new Thread(new Conductor(this, cantTerminales), "Conductor");
            conductor.start();
        }
    }

    private String cadenaTerminal(int numeroTerminal) {
        if (numeroTerminal < 1 || numeroTerminal > 26) {
            return "?";
        }
        return Character.toString((char) ('A' + numeroTerminal - 1));
    }
}
