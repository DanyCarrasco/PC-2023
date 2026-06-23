package tpObligatorio;

public class Cajero implements Runnable {
    private FreeShop tienda;

    public Cajero(FreeShop tienda) {
        this.tienda = tienda;
    }

    public void run() {
        try {
            while (true) {
                tienda.procesarPago();
                Thread.sleep(3000);
                tienda.entregarTicketCompra();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
