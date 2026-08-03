package tpObligatorio;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SalaEmbarque {
    private int CAPACIDAD_VUELO = 4;
    private String nombreTerminal;
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition llamado = lock.newCondition();
    private BlockingQueue<String> pendientes = new LinkedBlockingQueue<>();
    private Map<String, Vuelo> vuelos = new HashMap<>();
    private Vuelo vueloActual;
    private int asignadosActual = 0;
    private int numeroVuelo = 0;

    public SalaEmbarque(String nombreTerminal) {
        this.nombreTerminal = nombreTerminal;
    }

    public String asignarVuelo() {
        lock.lock();
        try {
            if (vueloActual == null || vueloActual.haDespegado() || asignadosActual >= CAPACIDAD_VUELO) {
                numeroVuelo++;
                vueloActual = new Vuelo(CAPACIDAD_VUELO, "VUELO-" + nombreTerminal + "-" + numeroVuelo);
                vuelos.put(vueloActual.getNombreVuelo(), vueloActual);
                asignadosActual = 0;
            }
            asignadosActual++;
            vueloActual.asignarPasajero();
            return vueloActual.getNombreVuelo();
        } finally {
            lock.unlock();
        }
    }

    public boolean esperarLlamado(String nombreVuelo) throws InterruptedException {
        boolean subio = true;
        lock.lock();
        try {
            Vuelo vuelo = vuelos.get(nombreVuelo);
            if (vuelo == null || vuelo.haDespegado()) {
                subio = false;
            }
            if (!vuelo.estaActivado()) {
                if (!pendientes.contains(nombreVuelo)) {
                    pendientes.offer(nombreVuelo);
                }
                System.out.println(Thread.currentThread().getName()
                        + " espera en la sala de embarque el llamado del " + nombreVuelo);
            }
            while (!vuelo.estaActivado() && !vuelo.haDespegado()) {
                llamado.await();
            }
            if (vuelo.haDespegado()) {
                subio = false;
            }
            subio = vuelo.abordar();
        } finally {
            lock.unlock();
        }
        return subio;
    }

    public Vuelo llamarSiguienteVuelo() throws InterruptedException {
        Vuelo vuelo;
        do {
            String nombre = pendientes.take();
            lock.lock();
            try {
                vuelo = vuelos.get(nombre);
                if (vuelo != null && !vuelo.estaActivado() && !vuelo.haDespegado()) {
                    vuelo.activar();
                    llamado.signalAll();
                } else {
                    vuelo = null;
                }
            } finally {
                lock.unlock();
            }
        } while (vuelo == null);
        return vuelo;
    }
}
