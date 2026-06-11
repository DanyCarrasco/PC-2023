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
            // Simula el recorrido por cada terminal
            for (int i = 1; i <= cantTerminales; i++) {
                Thread.sleep(1000);
                transporte.confirmarParada(i);
                System.out.println("Conductor confirma parada en terminal " + cadenaTerminal(i));
            }
            // transporte.reiniciarRecorrido(); // Se elimina porque causa reset innecesario
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private String cadenaTerminal(int numTerminal) {
        // ingresa un numero que sea entre 1 y cantidadTerminales
        return Character.toString('A' + numTerminal - 1);
    }
}
