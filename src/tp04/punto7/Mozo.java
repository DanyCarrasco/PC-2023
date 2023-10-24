package punto7;

public class Mozo implements Runnable {
    private Confiteria silla;
    private int cantidad;

    public Mozo(Confiteria silla, int cant) {
        this.silla = silla;
        this.cantidad = cant;
    }

    public void run() {
        int i = 0;
        while (i < cantidad) {
            silla.esperarPedido();
            prepararPedido();
            silla.entregarComida();
            System.out.println("Mozo sigue inventando versiones de pollo");
            i++;
        }
    }

    private void prepararPedido() {
        // Simula el tiempo de preparar el pedido
        System.out.println("Mozo anota el pedido y lo prepara");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Mozo recibe el pedido y lo lleva al empleado");
    }
}
