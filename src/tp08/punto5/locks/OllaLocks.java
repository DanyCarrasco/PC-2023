package tp08.punto5.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OllaLocks {
    private Lock mutex;
    private Condition canibales, cocinero;
    private int cantidad, numRaciones;
    private boolean avisaCocinero;

    public OllaLocks(int cantRaciones) {
        numRaciones = cantRaciones;
        cantidad = numRaciones;
        mutex = new ReentrantLock();
        canibales = mutex.newCondition();
        cocinero = mutex.newCondition();
        avisaCocinero = true;
    }

    public void comer() {
        try {
            mutex.lock();
            while (cantidad == 0) {
                if (avisaCocinero) {
                    System.out.println(Thread.currentThread().getName() + " avisa a cocinero");
                    avisaCocinero = false;
                    cocinero.signal();
                }
                System.out.println(Thread.currentThread().getName() + " ESPERA POR LA OLLA...");
                canibales.await();
            }
            System.out.println(Thread.currentThread().getName() + " come una racion de comida");
            cantidad--;
        } catch (InterruptedException e) {
        } finally {
            mutex.unlock();
        }
    }

    public void cocinar() {
        try {
            mutex.lock();
            while (cantidad > 0) {
                System.out.println(Thread.currentThread().getName() + " SIGUE DURMIENDO");
                cocinero.await();
            }
            System.out.println(Thread.currentThread().getName() + " cocino las raciones en la olla");
            cantidad = numRaciones;
            canibales.signalAll();
        } catch (InterruptedException e) {
        } finally {
            mutex.unlock();
        }
    }

}
