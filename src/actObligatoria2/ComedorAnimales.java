package actObligatoria2;

import java.util.concurrent.Semaphore;

public class ComedorAnimales {
    private int platos;
    private Semaphore comedero;
    private Semaphore mutex;
    private int perrosComiendo;
    private int gatosComiendo;

    public ComedorAnimales(int capacidad) {
        comedero = new Semaphore(capacidad);
        mutex = new Semaphore(1);
        perrosComiendo = 0;
        gatosComiendo = 0;
        this.platos = capacidad;
    }

    public void comerPerro() throws InterruptedException {
        mutex.acquire();
        while (perrosComiendo > 0 || gatosComiendo == this.platos) {
            mutex.release();
            Thread.sleep(100); // Esperar un poco antes de intentar nuevamente
            mutex.acquire();
        }
        perrosComiendo++;
        mutex.release();

        mutex.acquire();
        this.platos++;
        mutex.release();

        comedero.acquire();
        // El perro está comiendo

        mutex.acquire();
        perrosComiendo--;
        mutex.release();

        comedero.release();

        mutex.acquire();
        this.platos--;
        mutex.release();
    }

    public void comerGato() throws InterruptedException {
        mutex.acquire();
        while (gatosComiendo > 0 || perrosComiendo == this.platos) {
            mutex.release();
            Thread.sleep(100); // Esperar un poco antes de intentar nuevamente
            mutex.acquire();
        }
        gatosComiendo++;
        mutex.release();

        mutex.acquire();
        this.platos--;
        mutex.release();

        comedero.acquire();
        // El gato está comiendo

        mutex.acquire();
        gatosComiendo--;
        mutex.release();

        comedero.release();

        mutex.acquire();
        this.platos++;
        mutex.release();
    }
}
