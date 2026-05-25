package tp02;

// del tp 2, ejercicio 8

public class CajeroRunnable implements Runnable {
    private String nombre;
    private Cliente2 cliente;
    private long initialTime;

    // Constructor y metodos de acceso

    public CajeroRunnable(String nombre, Cliente2 cliente, long initialTime) {
        this.nombre = nombre;
        this.cliente = cliente;
        this.initialTime = initialTime;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Cliente2 getCliente() {
        return this.cliente;
    }

    public long getInitialTime() {
        return this.initialTime;
    }

    public void esperarXsegundos(int producto) {
        try {
            Thread.sleep((producto + (producto - 1)) * 1000);
        } catch (InterruptedException e) {
            System.out.println(this.nombre + " Interrumpido.");
        }
    }

    public void run() {
        System.out.println(
                "El cajero " + this.nombre + " COMIENZA A PROCESAR LA COMPRA DEL CLIENTE " + this.cliente.getNombre()
                        + " EN EL TIEMPO " + (System.currentTimeMillis() - this.initialTime) / 1000 + "seg");

        for (int i = 0; i < this.cliente.getCarroCompra().length; i++) {
            this.esperarXsegundos(cliente.getCarroCompra()[i]);
            System.out.println("Procesando el producto " + (i + 1) + " del cliente " + this.cliente.getNombre()
                    + "->Tiempo: " + (System.currentTimeMillis() - this.initialTime) / 1000 + "seg");
        }
        System.out.println("El cajero " + this.nombre + " HA TERMINADO DE PROCESAR " + this.cliente.getNombre()
                + " EN EL TIEMPO: " + (System.currentTimeMillis() - this.initialTime) / 1000 + "seg");
    }
}
