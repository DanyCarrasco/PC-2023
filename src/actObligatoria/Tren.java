package actObligatoria;

import java.util.concurrent.Semaphore;

public class Tren {
    private Semaphore arranqueTren = new Semaphore(1, true);
    private Semaphore semPasajero = new Semaphore(1, true);
    private int cantDisponible, cantPasajeros;

    public Tren(int cantDisponible) {
        this.cantDisponible = cantDisponible;
        this.cantPasajeros = 0;
    }

    public boolean hayLugar() {
        return cantPasajeros < cantDisponible;
    }

    public void ingresarATren() throws InterruptedException {
        cantPasajeros++;
        if (cantPasajeros == cantDisponible) {
            semPasajero.acquire();
            arranqueTren.release();
        }
    }

    public void esperarPasajeros() throws InterruptedException {
        this.semPasajero.acquire();
    }

    public void liberarPasajeros() throws InterruptedException {
        cantPasajeros = 0;
        arranqueTren.acquire();
        semPasajero.release();
    }

}
