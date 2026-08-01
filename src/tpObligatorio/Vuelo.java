package tpObligatorio;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Vuelo implements Runnable {
    private static final long TIEMPO_ESPERA_EMBARQUE = 10000;
    private CountDownLatch pasajerosAbordo;
    private String nombreVuelo;

    public Vuelo(int cantidadPasajeros, String nombreVuelo) {
        this.pasajerosAbordo = new CountDownLatch(cantidadPasajeros);
        this.nombreVuelo = nombreVuelo;
    }

    public void abordar() {
        pasajerosAbordo.countDown();
    }

    public String getNombreVuelo() {
        return nombreVuelo;
    }

    public void run() {
        try {
            boolean todosAbordo = pasajerosAbordo.await(TIEMPO_ESPERA_EMBARQUE, TimeUnit.MILLISECONDS);
            if (todosAbordo) {
                System.out.println("El vuelo " + nombreVuelo + " DESPEGA CON TODOS SUS PASAJEROS A BORDO");
            } else {
                System.out.println("El vuelo " + nombreVuelo
                        + " DESPEGA A HORARIO, sin los pasajeros que llegaron tarde al embarque");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
