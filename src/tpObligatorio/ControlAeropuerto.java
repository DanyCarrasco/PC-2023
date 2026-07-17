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
        try {
            while (!abierto) {
                pasajeros.await();
            }
            System.out.println(Thread.currentThread().getName() + " entro al aeropuerto");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void cerrarAeropuerto() {
        lock.lock();
        try {
            administrador.await(5, TimeUnit.SECONDS);
            abierto = false;
            System.out.println(Thread.currentThread().getName() + " cierra ingreso de pasajeros al aeropuerto VIAJE BONITO");
            pasajeros.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public boolean getEntradaAeropuerto() {
        return abierto;
    }

}
