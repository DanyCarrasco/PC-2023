package tp07.punto2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Recurso {
    private Lock mutex = new ReentrantLock();
    private Condition hayComputadoras, hayLibros;
    private int cantComputadoras, cantLibros;

    public Recurso(int numConputadoras, int numLibros) {
        cantComputadoras = numConputadoras;
        cantLibros = numLibros;
        hayComputadoras = mutex.newCondition();
        hayLibros = mutex.newCondition();
    }

    public void acceso() {
        try {
            mutex.lock();
            while (cantComputadoras == 0) {
                System.out.println(Thread.currentThread().getName() + " SIGUE ESPERANDO POR UNA COMPUTADORA...");
                hayComputadoras.await();
            }
            cantComputadoras--;
            while (cantLibros == 0) {
                System.out.println(Thread.currentThread().getName() + " SIGUE ESPERANDO POR UN LIBRO...");
                hayLibros.await();
            }
            cantLibros--;
            System.out.println(Thread.currentThread().getName() + " ingreso a una compuradora y un libro");
        } catch (InterruptedException e) {
        } finally {
            mutex.unlock();
        }
    }

    public void salir() {
        try {
            mutex.lock();
            cantComputadoras++;
            hayComputadoras.signalAll();
            cantLibros++;
            hayLibros.signalAll();
            System.out.println(Thread.currentThread().getName() + " sale dejando una computadora y un libro");
        } finally {
            mutex.unlock();
        }
    }
}
