package punto5;

import java.util.concurrent.Semaphore;

public class Impresora {
    private Semaphore accesoImpresora;
    private int id;
    private String tipo;
    private boolean disponible;

    public Impresora(int id, String tipo) {
        this.id = id;
        accesoImpresora = new Semaphore(1, true);
        this.disponible = true;
        this.tipo = tipo;
    }

    public void imprimir(String nombre, String tipoImpresion) {
        try {
            disponible = false;
            accesoImpresora.acquire();
            System.out.println(
                    nombre + " esta imprimiendo " + tipoImpresion + " con impresora " + id + " de tipo " + tipo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void terminar(String nombre, String tipoImpresion) {
        System.out.println(
                nombre + " termina de imprimir " + tipoImpresion + " con impresora " + id + " de tipo " + tipo);
        disponible = true;
        accesoImpresora.release();
    }

    public boolean estaDisponible() {
        return this.disponible;
    }

    public boolean puedeImprimir(String tipoImpresion) {
        boolean puede = tipoImpresion.equalsIgnoreCase(tipo);
        if (!puede) {
            puede = tipoImpresion.equalsIgnoreCase("x");
        }
        return puede;
    }
}
