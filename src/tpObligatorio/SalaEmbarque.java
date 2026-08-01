package tpObligatorio;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SalaEmbarque {
    private String nombreTerminal;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition pasajeroLlego = lock.newCondition();
    private final Condition llamado = lock.newCondition();
    private int pasajerosEsperando = 0;
    private int grupo = 0;
    private int numeroVuelo = 0;
    private Map<Integer, Vuelo> vuelosPorGrupo = new HashMap<>();

    public SalaEmbarque(String nombreTerminal) {
        this.nombreTerminal = nombreTerminal;
    }

    public void esperarLlamado() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()
                    + " espera en la sala de embarque el llamado a embarcar");

            pasajerosEsperando++;
            int miGrupo = grupo;
            pasajeroLlego.signal();

            while (grupo == miGrupo) {
                llamado.await();
            }
            Vuelo miVuelo = vuelosPorGrupo.get(miGrupo);
            if (miVuelo != null) {
                miVuelo.abordar();
                System.out.println(Thread.currentThread().getName()
                        + " sube al " + miVuelo.getNombreVuelo());
            }
        } finally {
            lock.unlock();
        }
    }

    public Vuelo llamarAEmbarcar() {
        lock.lock();
        try {
            int cantidadPasajeros = pasajerosEsperando;
            numeroVuelo++;
            Vuelo vuelo = new Vuelo(cantidadPasajeros, "VUELO-" + nombreTerminal + "-" + numeroVuelo);
            vuelosPorGrupo.put(grupo, vuelo);
            System.out.println("*** " + Thread.currentThread().getName()
                    + " LLAMA A EMBARCAR A PASAJEROS DEL TERMINAL " + nombreTerminal + " ***");
            grupo++;
            pasajerosEsperando = 0;
            llamado.signalAll();
            return vuelo;
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
