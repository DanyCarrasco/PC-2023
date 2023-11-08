package tp05.punto1;

import java.util.concurrent.Semaphore;

public class GestorPiscina {
    private Semaphore espacios;

    public GestorPiscina(int capacidad) {
        this.espacios = new Semaphore(capacidad, true);
    }

    public void ingresar() {
        try {
            this.espacios.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void salir() {
        this.espacios.release();
    }

}
