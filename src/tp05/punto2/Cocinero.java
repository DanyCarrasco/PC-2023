package tp05.punto2;

public class Cocinero implements Runnable {
    private Confiteria sillas;

    public Cocinero(Confiteria sillas) {
        this.sillas = sillas;
    }

    public void run() {
        while (true) {
            sillas.cocineroEsperaPedido();
            prepararComida();
            sillas.entregaDeCocinero();
            System.out.println("Cocinero sigue ordenando su cocina");
        }
    }

    private void prepararComida() {
        // Simula el tiempo de preparar la comida
        System.out.println("Cocinero recibe el aviso y prepara la comida");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Cocinero termina y lleva la comida al empleado");
    }
}
