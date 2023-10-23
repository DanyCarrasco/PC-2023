package punto6;

import java.util.concurrent.Semaphore;

public class Taxi {
    private Semaphore tomarTaxi;
    private Semaphore avisoDestino;
    private Semaphore mutex;

    public Taxi(){
        this.mutex = new Semaphore(1,true);
        this.tomarTaxi = new Semaphore(0);
        this.avisoDestino = new Semaphore(0);
    }

    public void subirAlTaxi(){
          try {
            this.mutex.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void solicitarTaxi(){
        this.tomarTaxi.release();
    }

    public void esperarSolicitud(){
        try {
            this.tomarTaxi.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void realizarAviso(){
        this.avisoDestino.release();
    }

    public void esperarAviso(){
        try {
            this.avisoDestino.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void bajarDelTaxi(){
        this.mutex.release();
    }
}
