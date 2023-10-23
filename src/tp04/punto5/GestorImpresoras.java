package punto5;

import java.util.concurrent.Semaphore;

public class GestorImpresoras {
    private Impresora[] impresoras;
    private int cantDisponible;
    private Semaphore accesoImpresoras;
    private Semaphore mutex;

    public GestorImpresoras(int cantImpresoras) {
        impresoras = new Impresora[cantImpresoras];
        for (int i = 0; i < cantImpresoras; i++) {
            if(i % 2 == 0){
                impresoras[i] = new Impresora(i, "A");
            }else {
                impresoras[i] = new Impresora(i,"B");
            }
        }
        cantDisponible = cantImpresoras;
        accesoImpresoras = new Semaphore(1, true);
        mutex = new Semaphore(1, true);
    }

    public Impresora usarImpresora(String tipoImpresion) {
        Impresora impresora = null;
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        impresora = asignarImpresora(tipoImpresion);
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

    private Impresora asignarImpresora(String tipoImpresion) {
        Impresora impresora = null;
        int i = 0;
        boolean encontro = false;
        while (!encontro && i < impresoras.length) {
            if (impresoras[i].estaDisponible() && impresoras[i].puedeImprimir(tipoImpresion)) {
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
