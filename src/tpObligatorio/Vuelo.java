package tpObligatorio;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Vuelo implements Runnable {
    private static final long TIEMPO_ESPERA_EMBARQUE = 5000;
    private final String nombreVuelo;
    private final int capacidad;
    private final CountDownLatch asientos;
    private final AtomicInteger abordo = new AtomicInteger(0);
    private final AtomicInteger asignados = new AtomicInteger(0);
    private volatile boolean activado = false;
    private volatile boolean despego = false;

    public Vuelo(int capacidad, String nombreVuelo) {
        this.capacidad = capacidad;
        this.nombreVuelo = nombreVuelo;
        this.asientos = new CountDownLatch(capacidad);
    }

    public String getNombreVuelo() {
        return nombreVuelo;
    }

    public boolean estaActivado() {
        return activado;
    }

    public boolean haDespegado() {
        return despego;
    }

    public void activar() {
        activado = true;
    }

    public void asignarPasajero() {
        asignados.incrementAndGet();
    }

    public boolean abordar() {
        if (!despego && asientos.getCount() > 0) {
            asientos.countDown();
            abordo.incrementAndGet();
            return true;
        }
        return false;
    }

    public void run() {
        try {
            System.out.println("*** " + Thread.currentThread().getName() + " LLAMA A EMBARCAR ***");
            asientos.await(TIEMPO_ESPERA_EMBARQUE, TimeUnit.MILLISECONDS);
            despego = true;
            if (abordo.get() >= asignados.get()) {
                System.out.println("El vuelo " + nombreVuelo + " DESPEGA CON TODOS SUS PASAJEROS A BORDO");
            } else {
                System.out.println("El vuelo " + nombreVuelo + " DESPEGA A HORARIO con "
                        + abordo.get() + " de " + asignados.get()
                        + " pasajeros a bordo (" + (asignados.get() - abordo.get())
                        + " perdieron el vuelo)");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
