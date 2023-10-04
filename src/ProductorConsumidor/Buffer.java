package ProductorConsumidor;

import java.util.concurrent.Semaphore;

public class Buffer {
    private int capacidad, cantidad;
    private Semaphore semVacio;
    private Semaphore semLleno;
    private Semaphore semDisponible;
    public Buffer(int capacidad) {
        this.capacidad = capacidad;
        this.cantidad = 0;
    }

    public void sacarItem() throws InterruptedException {
        this.semVacio.acquire();
        System.out.println("Sacando item");
        this.cantidad--;
    }

    public void agregarItem(int item) throws InterruptedException {
        this.semLleno.acquire();
        System.out.println("Agregando item "+item);
        tiempo();
    }

    private void tiempo(){
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
