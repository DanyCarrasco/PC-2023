package tpObligatorio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class FreeShop {
    private String idTerminal;
    private ArrayBlockingQueue<Thread> capacidad;
    private boolean pagoPendiente = false;
    private boolean pagoCompletado = false;

    private int cantidad;
    private ReentrantLock lock;
    private Condition cajeros, pasajeros;

    public FreeShop(String idTerminal, int lugar){
        this.idTerminal = idTerminal;
        this.capacidad = new ArrayBlockingQueue<>(lugar);

        this.cantidad = lugar;
        this.lock = new ReentrantLock();
        cajeros = lock.newCondition();
        pasajeros = lock.newCondition();
    }

    public boolean ingresarFreeShop(long tiempoMaxEspera){
        boolean ingresa = false;
        lock.lock();
        try {
            ingresa = capacidad.offer(Thread.currentThread(), tiempoMaxEspera, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return ingresa;
    }

    public void salirFreeShop(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+ " mira los productos y sale del Free Shop de la terminal "+ idTerminal);
            capacidad.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void comprarEnFreeShop(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+ " compra en Free Shop");
            System.out.println(Thread.currentThread().getName()+" avisa a los cajeros que quiere pagar");
            this.pagoPendiente = true;
            cajeros.signal();
            while(!pagoCompletado){
                pasajeros.await();
            }
            this.pagoCompletado = false;
            System.out.println(Thread.currentThread().getName()+ " se lleva su producto y sale del Free Shop de la terminal "+ idTerminal);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }
    }

    public void procesarPago(){
        lock.lock();
        try {
            while (!pagoPendiente) {
                cajeros.await();
            }
            System.out.println(Thread.currentThread().getName()+ " recibe aviso y recibe el pago del producto del pasajero");
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }
    }

    public void entregarTicketCompra(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+ " entrega el ticket de la compra al pasajero");
            this.pagoPendiente = false;
            this.pagoCompletado = true;
            pasajeros.signal();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }
    }


}
