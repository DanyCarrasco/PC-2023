package tpObligatorio;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SalaEmbarque {
    private String nombreTerminal;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition pasajeroLlego = lock.newCondition();
    private final Condition llamado = lock.newCondition();
    private int pasajerosEsperando = 0;
    private int grupo = 0; // incrementa cada vez que el empleado llama a embarcar

    public SalaEmbarque(String nombreTerminal) {
        this.nombreTerminal = nombreTerminal;
    }

    // Pasajero espera en la sala de embarque hasta que el empleado llame a embarcar.
    // Si el llamado ya esta activo (generacion > 0), entra directamente.
    public void esperarLlamado() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()
                    + " espera en la sala de embarque el llamado a embarcar");

            int miGrupo = grupo; // guarda el grupo actual
            pasajerosEsperando++;
            pasajeroLlego.signal(); // avisa al empleado que hay al menos 1 pasajero

            while (grupo == miGrupo) {
                llamado.await();
            }
            // grupo cambio: el empleado llamo a embarcar para este grupo
            System.out.println(Thread.currentThread().getName()
                    + " escucha el llamado a embarcar y sube al avion");
        } finally {
            lock.unlock();
        }
    }

    // Empleado llama a embarcar: incrementa la generacion y seniala a todos
    // los pasajeros que estan esperando de ESTE ciclo.
    // Los que lleguen despues quedan en la proxima generacion.
    public void llamarAEmbarcar() {
        lock.lock();
        try {
            System.out.println("*** " + Thread.currentThread().getName()
                    + " LLAMA A EMBARCAR A PASAJEROS DEL TERMINAL " + nombreTerminal + " ***");
            grupo++; // nuevo grupo: todos los que estan esperando pasan
            pasajerosEsperando = 0;
            llamado.signalAll(); // despierta a los pasajeros de este grupo
        } finally {
            lock.unlock();
        }
    }

    // Empleado espera a que haya al menos un pasajero en la sala antes de llamar.
    public void esperarPasajero() throws InterruptedException {
        lock.lock();
        try {
            while (pasajerosEsperando == 0) {
                pasajeroLlego.await();
            }
            // Ya hay pasajeros, el empleado puede proceder a llamarAEmbarcar()
        } finally {
            lock.unlock();
        }
    }
}
