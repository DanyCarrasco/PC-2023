package tpObligatorio;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ControlAeropuerto {

    public Aeropuerto aeropuerto;
    private ReentrantLock lock;
    private Condition administrador, pasajeros;
    private boolean abierto;
    // Tiempo que el administrador espera antes de cerrar (simula las 6:00-22:00)
    private static final int TIEMPO_ABIERTO_SEGUNDOS = 30;

    public ControlAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
        this.lock = new ReentrantLock();
        this.administrador = lock.newCondition();
        this.pasajeros = lock.newCondition();
        this.abierto = false;
    }

    public void abrirAeropuerto() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " abre el aeropuerto VIAJE BONITO");
            abierto = true;
            pasajeros.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // Pasajero intenta entrar al aeropuerto. Retorna true si entro, false si esta cerrado.
    // La lectura de abierto se hace DENTRO del lock, eliminando la race condition.
    public boolean entrarAlAeropuerto() {
        lock.lock();
        try {
            while (!abierto) {
                pasajeros.await();
            }
            System.out.println(Thread.currentThread().getName() + " entro al aeropuerto");
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            lock.unlock();
        }
    }

    // Administrador cierra el aeropuerto despues de un tiempo
    public void cerrarAeropuerto() {
        lock.lock();
        try {
            administrador.await(TIEMPO_ABIERTO_SEGUNDOS, TimeUnit.SECONDS);
            abierto = false;
            System.out.println(Thread.currentThread().getName()
                    + " cierra ingreso de pasajeros al aeropuerto VIAJE BONITO");
            pasajeros.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
