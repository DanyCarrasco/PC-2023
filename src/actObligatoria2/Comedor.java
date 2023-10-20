package actObligatoria2;

import java.util.concurrent.Semaphore;

public class Comedor {
    private int platos;
    private Semaphore comedero;
    private Semaphore mutex;
    private int perrosComiendo;
    private int gatosComiendo;

    public Comedor(int capacidad) {
        comedero = new Semaphore(capacidad);
        mutex = new Semaphore(1);
        perrosComiendo = 0;
        gatosComiendo = 0;
        this.platos = capacidad;
    }

    public void entrar(String tipo) throws InterruptedException {
        if (tipo.equals("Perro")) {
            entraPerro();
        } else {
            entraGato();
        }
    }

    private void entraPerro() throws InterruptedException {
        mutex.acquire();
        while (this.gatosComiendo > 0 || this.platos == 0) {
            // Si hay gatos comiendo, espera
            mutex.release();
            // Espera un poco antes de intentar nuevamente
            Thread.sleep(100);
            mutex.acquire();
        }
        perrosComiendo++;
        mutex.release();

        mutex.acquire();
        this.platos--;
        mutex.release();

        comedero.acquire();
    }

    private void entraGato() throws InterruptedException {
        mutex.acquire();
        while (this.perrosComiendo > 0 || this.platos == 0) {
            // Si hay gatos comiendo, espera
            mutex.release();
            // Esperar un poco antes de intentar nuevamente
            Thread.sleep(100);
            mutex.acquire();
        }
        gatosComiendo++;
        mutex.release();

        mutex.acquire();
        this.platos--;
        mutex.release();

        comedero.acquire();
    }

    public void salir(String tipo) throws InterruptedException {
        if (tipo.equals("Perro")) {
            salePerro();
        } else {
            saleGato();
        }
    }

    private void salePerro() throws InterruptedException {
        mutex.acquire();
        perrosComiendo--;
        mutex.release();

        comedero.release();

        mutex.acquire();
        this.platos++;
        mutex.release();
    }

    private void saleGato() throws InterruptedException {
        mutex.acquire();
        gatosComiendo--;
        mutex.release();

        comedero.release();

        mutex.acquire();
        this.platos++;
        mutex.release();
    }
}
