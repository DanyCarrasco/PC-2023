package tp05.punto2;

import java.util.concurrent.Semaphore;

public class Confiteria {
    private Semaphore espacios;
    private Semaphore pedidoMozo;
    private Semaphore entregaMozo;
    private Semaphore pedidoCocinero;
    private Semaphore entregaCocinero;

    public Confiteria() {
        this.espacios = new Semaphore(2, true);
        this.pedidoMozo = new Semaphore(0);
        this.entregaMozo = new Semaphore(0);
        this.pedidoCocinero = new Semaphore(0);
        this.entregaCocinero = new Semaphore(0);
    }

    public void tomarAsiento() {
        try {
            this.espacios.acquire();
        } catch (InterruptedException e) {
        }
    }

    public void dejarAsiento() {
        this.espacios.release();
    }

    public void pedirAMozo() {
        this.pedidoMozo.release();
    }

    public void mozoEsperaPedido() {
        try {
            this.pedidoMozo.acquire();
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }

    public void entregaDeMozo() {
        this.entregaMozo.release();
    }

    public void esperaPedidoMozo() {
        try {
            this.entregaMozo.acquire();
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }

    public void pedirACocinero() {
        this.pedidoCocinero.release();
    }

    public void cocineroEsperaPedido() {
        try {
            this.pedidoCocinero.acquire();
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }

    public void entregaDeCocinero() {
        this.entregaCocinero.release();
    }

    public void esperaPedidoCocinero() {
        try {
            this.entregaCocinero.acquire();
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }
}
