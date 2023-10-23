package punto7;

import java.util.concurrent.Semaphore;

public class Confiteria {
    private Semaphore espacio;
    private Semaphore pedido;
    private Semaphore comida;

    public Confiteria(){
        this.espacio = new Semaphore(1, true);
        this.pedido = new Semaphore(0);
        this.comida = new Semaphore(0);
    }

    public void solicitarSilla(){
        try {
            this.espacio.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void realizarPedido(){
        this.pedido.release();
    }

    public void esperarPedido(){
        try {
            this.pedido.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void entregarComida(){
        this.comida.release();
    }

    public void esperarComida(){
        try {
            this.comida.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void dejarSilla(){
        this.espacio.release();
    }
}
