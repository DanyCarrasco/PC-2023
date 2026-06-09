package tpObligatorio;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class TransporteATerminal {
    private CyclicBarrier barrera;
    private Runnable accionInicio;
    private final Semaphore mutex;
    private final Semaphore maximoPasajeros;
    private final Semaphore[] pasajerosDeTerminal;
    private final Semaphore[] barreraTerminal; // sincroniza salida por terminal
    private int paradaTerminal = 0;
    private final int cantTerminales;
    private int pasajerosAbordo = 0;

    public TransporteATerminal(int cantidad, int cantidadTerminales) {
        this.accionInicio = avisarConductor();
        this.barrera = new CyclicBarrier(cantidad, accionInicio);
        this.mutex = new Semaphore(1, true);
        this.maximoPasajeros = new Semaphore(cantidad, true);
        this.cantTerminales = cantidadTerminales;
        this.pasajerosDeTerminal = new Semaphore[cantidadTerminales];
        this.barreraTerminal = new Semaphore[cantidadTerminales];
        for (int i = 0; i < cantidadTerminales; i++) {
            this.pasajerosDeTerminal[i] = new Semaphore(0, true);
            this.barreraTerminal[i] = new Semaphore(0, true);
        }
    }

    // Pasajero sube al transporte
    public void subirATransporte(int numeroTerminal) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " intenta subir al transporte");
        maximoPasajeros.acquire();
        System.out.println(Thread.currentThread().getName() + " sube al transporte");

        // El pasajero avisa que va a esta terminal (semáforo destino)
        pasajerosDeTerminal[numeroTerminal - 1].release();

        // Espera a que el transporte arranque (todos los cupos llenos)
        barrera.await();
    }

    // Conductor llega a una parada
    public void llegadaParada(int parada) throws InterruptedException {
        mutex.acquire();
        try {
            this.paradaTerminal = parada;
            System.out.println(Thread.currentThread().getName()
                    + " llega a la parada " + cadenaTerminal(parada));
        } finally {
            mutex.release();
        }
    }

    // Pasajero baja cuando es su parada
    public void bajarDelTransporte(int numeroTerminal) throws InterruptedException {
        // Espera bloqueante hasta que sea su parada
        while (true) {
            mutex.acquire();
            try {
                if (paradaTerminal == numeroTerminal) {
                    break; // salir del while, mantener mutex
                }
            } finally {
                mutex.release();
            }
            // No es su parada: espera un poco y reintenta
            Thread.sleep(50);
        }
        // Aquí tenemos el mutex tomado y es nuestra parada
        try {
            System.out.println(Thread.currentThread().getName()
                    + " baja del transporte en la terminal " + cadenaTerminal(numeroTerminal));
            pasajerosDeTerminal[numeroTerminal - 1].acquire(); // espera que el conductor confirme
            maximoPasajeros.release(); // libera el cupo
        } finally {
            mutex.release();
        }
    }

    private Runnable avisarConductor() {
        return new Conductor(this, this.cantTerminales);
    }

    private String cadenaTerminal(int numeroTerminal) {
        if (numeroTerminal < 1 || numeroTerminal > 26) {
            return "?";
        }
        return Character.toString(('A' + numeroTerminal - 1));
    }
}