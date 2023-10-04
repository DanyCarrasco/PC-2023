package tp04;

import java.util.concurrent.Semaphore;

public class GestorImpresoras {
    private boolean[] impresoras;
    private int cantDisponible;
    private Semaphore accesoImpresoras;
    public GestorImpresoras(int cant){
        impresoras = new boolean[cant];
        cantDisponible = cant;
        accesoImpresoras = new Semaphore(1, true);
    }
    private void iniciarImpresoras(int cant){
        for (int i = 0; i < cant; i++) {
            this.impresoras[i] = true;
        }
    }

    private void usarImpresora()
    }
}
