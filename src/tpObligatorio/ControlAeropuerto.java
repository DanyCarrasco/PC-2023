package tpObligatorio;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ControlAeropuerto {

    public Aeropuerto aeropuerto;
    private ReentrantLock lock;
    private Condition administrador, pasajeros;
    private boolean abierto;

    public ControlAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
        this.lock = new ReentrantLock();
        this.administrador = lock.newCondition();
        this.pasajeros = lock.newCondition();
        this.abierto = false;
    }

    public void abrirAeropuerto() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " abre el aeropuerto VIAJE BONITO");
        abierto = true;
        pasajeros.signalAll();
        lock.unlock();
    }

    public void entrarAlAeropuerto() {
        lock.lock();
        while (!abierto) {
            try {
                pasajeros.await(0, null);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " entro al aeropuerto");
        lock.unlock();
    }

    public void cerrarAeropuerto() {
        lock.lock();
        try {
            administrador.await(10, TimeUnit.SECONDS);
            abierto = false;
            System.out.println(Thread.currentThread().getName() + " cierra ingreso de pasajeros al aeropuerto VIAJE BONITO");
            pasajeros.signalAll();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public boolean getEntradaAeropuerto() {
        return abierto;
    }

}
