package tpObligatorio;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SalaEmbarque {
    private String nombreTerminal;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition pasajeroLlego = lock.newCondition();
    private final Condition llamado = lock.newCondition();
    private int pasajerosEsperando = 0;
    private int grupo = 0;

    public SalaEmbarque(String nombreTerminal) {
        this.nombreTerminal = nombreTerminal;
    }

    public void esperarLlamado() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()
                    + " espera en la sala de embarque el llamado a embarcar");

            int miGrupo = grupo;
            pasajerosEsperando++;
            pasajeroLlego.signal();

            while (grupo == miGrupo) {
                llamado.await();
            }
            System.out.println(Thread.currentThread().getName()
                    + " escucha el llamado a embarcar y sube al avion");
        } finally {
            lock.unlock();
        }
    }

    public void llamarAEmbarcar() {
        lock.lock();
        try {
            System.out.println("*** " + Thread.currentThread().getName()
                    + " LLAMA A EMBARCAR A PASAJEROS DEL TERMINAL " + nombreTerminal + " ***");
            grupo++;
            pasajerosEsperando = 0;
            llamado.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void esperarPasajero() throws InterruptedException {
        lock.lock();
        try {
            while (pasajerosEsperando == 0) {
                pasajeroLlego.await();
            }
        } finally {
            lock.unlock();
        }
    }
}
