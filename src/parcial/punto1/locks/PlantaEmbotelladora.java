package parcial.punto1.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PlantaEmbotelladora {
    private int cantBotellasAgua;
    private int cantBotellasVino;
    private int cantCajasAlmacen;

    private char etiquetaCaja;

    private Lock mutex;
    private Condition embotelladoresAgua;
    private Condition embotelladoresVino;
    private Condition empaquetador;
    private Condition transportador;
    private Condition cajaVacia;
    private Condition almacenVacio;

    public PlantaEmbotelladora() {
        cantBotellasAgua = 0;
        cantBotellasVino = 0;
        cantCajasAlmacen = 0;

        mutex = new ReentrantLock();
        embotelladoresAgua = mutex.newCondition();
        embotelladoresVino = mutex.newCondition();
        empaquetador = mutex.newCondition();
        transportador = mutex.newCondition();
        cajaVacia = mutex.newCondition();
        almacenVacio = mutex.newCondition();
    }

    public void guardarBotella(boolean botellaAgua) {
        mutex.lock();
        if (botellaAgua) {
            guardarEnCajaA();
        } else {
            guardarEnCajaV();
        }
        mutex.unlock();
    }

    private void guardarEnCajaA() {
        try {
            while (cantBotellasAgua == 10) {
                embotelladoresAgua.await();
            }

            cantBotellasAgua++;
            System.out.println(Thread.currentThread().getName()+" guardo una botella de agua en caja, la caja tiene "+cantBotellasAgua);
            if (cantBotellasAgua == 10) {
                etiquetaCaja = 'A';
                System.out.println("Se lleno la caja con botellas de agua");
                empaquetador.signal();
                System.out.println("Se espera una caja de agua vacia");
                cajaVacia.await();
                System.out.println("Se reemplaza con una caja de vino vacia");
            }
        } catch (InterruptedException e) {
        }
    }

    private void guardarEnCajaV() {
        try {
            while(cantBotellasVino == 10){
                embotelladoresVino.await();
            }
            cantBotellasVino++;
            System.out.println(Thread.currentThread().getName()+" guardo una botella de vino en caja, la caja tiene "+cantBotellasVino);
            if (cantBotellasVino == 10) {
                etiquetaCaja = 'V';
                System.out.println("Se lleno la caja con botellas de vino");
                empaquetador.signal();
                System.out.println("Se espera una caja de vino vacia");
                cajaVacia.await();
                System.out.println("Se reemplaza con una caja de vino vacia");
            }
        } catch (InterruptedException e) {
        }
    }

    public void guardarCaja() {
        try {
            while(cantBotellasVino != 10 && cantBotellasAgua != 10){
                empaquetador.await();
            }
            cantCajasAlmacen++;
            System.out.println("Se guarda 1 caja en el almacen, el almacen tiene "+cantCajasAlmacen+" cajas");
            if (cantCajasAlmacen == 10) {
                System.out.println("El almacen esta lleno");
                transportador.signal();
                System.out.println("Empaquetador espera que se vacie el almacen");
                almacenVacio.signal();
            }
        } catch (InterruptedException e) {
        }
    }

    public void reponerCaja() {
        if (etiquetaCaja == 'A') {
            cantBotellasAgua = 0;
            embotelladoresAgua.signalAll();
        } else {
            cantBotellasVino = 0;
            embotelladoresVino.signalAll();
        }
    }

    public void repartirCajas() {
        try {
            while(cantCajasAlmacen != 10){
                transportador.await();
            }
            cantCajasAlmacen = 0;
            almacenVacio.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
