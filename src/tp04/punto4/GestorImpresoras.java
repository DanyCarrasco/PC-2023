package punto4;

import java.util.concurrent.Semaphore;

public class GestorImpresoras {
    private Impresora[] impresoras;
    private int cantDisponible;
    private Semaphore accesoImpresoras;
    private Semaphore mutex;

    public GestorImpresoras(int cantImpresoras) {
        impresoras = new Impresora[cantImpresoras];
        for (int i = 0; i < cantImpresoras; i++) {
            impresoras[i] = new Impresora(i);
        }
        cantDisponible = cantImpresoras;
        accesoImpresoras = new Semaphore(1, true);
        mutex = new Semaphore(1, true);
    }

    public Impresora usarImpresora() {
        Impresora impresora = null;
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        impresora = asignarImpresora();
        if (impresora != null) {
            cantDisponible--;
            if (cantDisponible == 0) {
                try {
                    accesoImpresoras.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        mutex.release();
        return impresora;
    }

    private Impresora asignarImpresora() {
        Impresora impresora = null;
        int i = 0;
        boolean encontro = false;
        while (!encontro && i < impresoras.length) {
            if (impresoras[i].estaDisponible()) {
                impresora = impresoras[i];
                cantDisponible--;
                encontro = true;
            }
            i++;
        }
        return impresora;
    }

    public void dejarImpresora() {
        if (cantDisponible == 0) {
            accesoImpresoras.release();
        }
        cantDisponible++;
    }
}
