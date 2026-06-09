package tpObligatorio;

public class Conductor implements Runnable {
    private TransporteATerminal transporte;
    private int cantTerminales;

    public Conductor(TransporteATerminal transporte, int cantidadTerminales) {
        this.transporte = transporte;
        this.cantTerminales = cantidadTerminales;
    }

    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " empieza el recorrido");
            for (int i = 1; i <= cantTerminales; i++) {
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName()+ " llega a la parada "+ cadenaTerminal(i));
                transporte.llegadaParada(i);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private String cadenaTerminal(int numTerminal) {
        // ingresa un numero que sea entre 1 y cantidadTerminales
        return Character.toString(('@' + numTerminal));
    }
}
