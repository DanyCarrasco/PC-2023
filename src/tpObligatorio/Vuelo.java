package tpObligatorio;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Vuelo implements Runnable {
    private long TIEMPO_ESPERA_EMBARQUE = 5000;
    private String nombreVuelo;
    private int capacidad;
    // Asientos que aún no fueron ocupados (abordo = capacidad - getCount()).
    private CountDownLatch asientos;
    // Asientos que aún no fueron asignados (asignados = capacidad - getCount()).
    private CountDownLatch asientosAsignados;
    // Eventos de una sola vez: llamada a embarcar y despegue.
    private CountDownLatch activado = new CountDownLatch(1);
    private CountDownLatch despego = new CountDownLatch(1);

    public Vuelo(int capacidad, String nombreVuelo) {
        this.capacidad = capacidad;
        this.nombreVuelo = nombreVuelo;
        this.asientos = new CountDownLatch(capacidad);
        this.asientosAsignados = new CountDownLatch(capacidad);
    }

    public String getNombreVuelo() {
        return nombreVuelo;
    }

    public boolean estaActivado() {
        return activado.getCount() == 0;
    }

    public boolean haDespegado() {
        return despego.getCount() == 0;
    }

    public void activar() {
        activado.countDown();
    }

    public void asignarPasajero() {
        asientosAsignados.countDown();
    }

    public boolean abordar() {
        boolean abordo = false;
        if (!haDespegado() && asientos.getCount() > 0) {
            asientos.countDown();
            abordo = true;
        }
        return abordo;
    }

    public void run() {
        try {
            System.out.println("*** " + Thread.currentThread().getName() + " LLAMA A EMBARCAR ***");
            asientos.await(TIEMPO_ESPERA_EMBARQUE, TimeUnit.MILLISECONDS);
            despego.countDown();
            int abordo = capacidad - (int) asientos.getCount();
            int asignados = capacidad - (int) asientosAsignados.getCount();
            if (abordo >= asignados) {
                System.out.println("El vuelo " + nombreVuelo + " DESPEGA CON TODOS SUS PASAJEROS A BORDO");
            } else {
                System.out.println("El vuelo " + nombreVuelo + " DESPEGA A HORARIO con "
                        + abordo + " de " + asignados
                        + " pasajeros a bordo (" + (asignados - abordo)
                        + " perdieron el vuelo)");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
