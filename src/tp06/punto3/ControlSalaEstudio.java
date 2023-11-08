package tp06.punto3;

import java.util.concurrent.Semaphore;

public class ControlSalaEstudio {
    private Semaphore sala;

    public ControlSalaEstudio(int limite) {
        this.sala = new Semaphore(limite, true);
    }

    public void ingresar() {
        try {
            this.sala.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void salir() {
        this.sala.release();
    }
}
