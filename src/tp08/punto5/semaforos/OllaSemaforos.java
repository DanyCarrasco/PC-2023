package tp08.punto5.semaforos;

import java.util.concurrent.Semaphore;

public class OllaSemaforos {
    private int cantidad, numRaciones;
    private Semaphore raciones, mutex;
    private Semaphore pedirRaciones, racionesListas;

    private boolean esPrimero;

    public OllaSemaforos(int numRaciones) {
        this.numRaciones = numRaciones;
        cantidad = numRaciones;
        raciones = new Semaphore(numRaciones);
        pedirRaciones = new Semaphore(0);
        racionesListas = new Semaphore(0);
        mutex = new Semaphore(1);
        esPrimero = true;
    }

    public void puedeComer() {
        try {
            mutex.acquire();
            if (cantidad == 0 && esPrimero) {
                esPrimero = false;
                System.out.println(Thread.currentThread().getName()+" avisa cocinero");
                avisoCocinero();
                System.out.println(Thread.currentThread().getName()+" espera por la olla");
                mutex.release(); //es necesario porque se pisaria con el semaforo que utiliza avisoCocinero
                if (esPrimero){
                    esperaAvisoCanibal();
                    mutex.acquire();
                    System.out.println(Thread.currentThread().getName()+" recibe aviso de cocinero");
                }
            }
            mutex.release();
            raciones.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void comer() {
        try {
            mutex.acquire();
            cantidad--;
            mutex.release();
        } catch (InterruptedException e) {
        }
    }

    private void avisoCocinero() {
        pedirRaciones.release();
    }

    public void esperaPedidoCocinero() {
        try {
            pedirRaciones.acquire();
        } catch (InterruptedException e) {
        }

    }

    public void avisoCanibal() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        cantidad = numRaciones;
        esPrimero = true;
        mutex.release();
        raciones.release(numRaciones);
        racionesListas.release();
    }

    private void esperaAvisoCanibal() {
        try {
            racionesListas.acquire();
        } catch (InterruptedException e) {
        }
    }

}
