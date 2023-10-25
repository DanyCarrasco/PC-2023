package tp05.punto1;

import java.util.concurrent.Semaphore;

public class GestorPiscina {
    private Semaphore mutex;
    private Semaphore espacios;
    private int capacidad;

    public GestorPiscina(int capacidad) {
        this.capacidad = capacidad;
        this.mutex = new Semaphore(1);
        this.espacios = new Semaphore(capacidad, true);
    }

    public void ingresar() {
        try {
            this.mutex.acquire();
            while (capacidad == 0) {
                this.mutex.release();
                // Espera por un tiempo
                System.out.println(Thread.currentThread().getName()+ " sigue esperando");
                Thread.sleep(5000);
                // Vuelve a tomar y a preguntar si puede ingresar a la piscina
                this.mutex.acquire();
            }
            capacidad--;
            this.mutex.release();
            this.espacios.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
public void salir(){
    try {
        this.mutex.acquire();
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    this.capacidad++;
    this.mutex.release();
    this.espacios.release();
}

}
