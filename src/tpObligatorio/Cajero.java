package tpObligatorio;

public class Cajero implements Runnable {
    private FreeShop tienda;

    public Cajero(FreeShop tienda) {
        this.tienda = tienda;
    }

    public void run() {
        int i = 0;
        try {
            while (i < 5) {
                tienda.procesarPago();
                Thread.sleep(3000);
                tienda.entregarTicketCompra();
                i++;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
