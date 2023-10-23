package punto4;

import java.util.concurrent.Semaphore;

public class Impresora {
    private Semaphore accesoImpresora;
    private int id;
    private boolean disponible;

    public Impresora(int id) {
        this.id = id;
        accesoImpresora = new Semaphore(1, true);
        this.disponible = true;
    }

    public void imprimir(String nombre) {
        try {
            disponible = false;
            accesoImpresora.acquire();
            System.out.println(nombre+" esta imprimiendo con impresora " + id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void terminar(String nombre) {
        System.out.println(nombre+ " termina de imprimir con impresora " + id);
        disponible = true;
        accesoImpresora.release();
    }

    public boolean estaDisponible() {
        return this.disponible;
    }
}
