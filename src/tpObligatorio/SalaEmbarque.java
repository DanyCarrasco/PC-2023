package tpObligatorio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SalaEmbarque {
    private static final int CAPACIDAD_VUELO = 4;
    private String nombreTerminal;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition vueloPendiente = lock.newCondition();
    private final Condition llamado = lock.newCondition();
    private final Queue<String> pendientes = new LinkedList<>();
    private final Map<String, Vuelo> vuelos = new HashMap<>();
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
        lock.lock();
        try {
            Vuelo vuelo = vuelos.get(nombreVuelo);
            if (vuelo == null || vuelo.haDespegado()) {
                return false;
            }
            if (!vuelo.estaActivado()) {
                if (!pendientes.contains(nombreVuelo)) {
                    pendientes.add(nombreVuelo);
                    vueloPendiente.signal();
                }
                System.out.println(Thread.currentThread().getName()
                        + " espera en la sala de embarque el llamado del " + nombreVuelo);
            }
            while (!vuelo.estaActivado() && !vuelo.haDespegado()) {
                llamado.await();
            }
            if (vuelo.haDespegado()) {
                return false;
            }
            boolean subio = vuelo.abordar();
            if (!subio) {
                return false;
            }
            System.out.println(Thread.currentThread().getName() + " sube al " + nombreVuelo);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public Vuelo llamarSiguienteVuelo() throws InterruptedException {
        lock.lock();
        try {
            while (true) {
                while (pendientes.isEmpty()) {
                    vueloPendiente.await();
                }
                String nombre = pendientes.poll();
                Vuelo vuelo = vuelos.get(nombre);
                if (vuelo != null && !vuelo.estaActivado() && !vuelo.haDespegado()) {
                    vuelo.activar();
                    llamado.signalAll();
                    return vuelo;
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
