package tpObligatorio;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class TransporteATerminal {
    // metodo para que pasajero suba
    // metodo para que conductor empiece a conducir
    // metodo para avisar a que terminal llegaron
    // metodo para que pasajero baje en su terminal, despues de que el conductor
    // avise que llegaron a la terminal
    // metodo para volver al inicio

    // Implementa un sistema de terminales en base a int,
    // pero debo cambiarlo por uno de String.

    private CyclicBarrier barrera;
    private Runnable accionInicio;
    private Semaphore mutex, maximoPasajeros;
    private Semaphore[] pasajerosDeTerminal;

    private int paradaTerminal = 0, cantTerminales;

    public TransporteATerminal(int cantidad, int cantidadTerminales) {
        this.accionInicio = avisarConductor();
        this.barrera = new CyclicBarrier(cantidad, accionInicio);
        this.mutex = new Semaphore(1);
        this.maximoPasajeros = new Semaphore(cantidad);
        this.cantTerminales = cantidadTerminales;
        this.pasajerosDeTerminal = new Semaphore[cantidadTerminales];
        for (int i = 0; i < cantidadTerminales; i++) {
            pasajerosDeTerminal[i] = new Semaphore(0);
        }
    }

    public void subirATransporte(int numeroTerminal) {
        System.out.println(Thread.currentThread().getName() + " intenta subir al transporte");
        try {
            maximoPasajeros.acquire();
            System.out.println(Thread.currentThread().getName() + " sube al transporte");
            barrera.await();
            pasajerosDeTerminal[numeroTerminal - 1].release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bajarDelTransporte(int numeroTerminal) throws InterruptedException {
        // cuando el conducto avisa que llego a su parada, se baja
        mutex.acquire();
        if (paradaTerminal == numeroTerminal) {
            System.out.println(Thread.currentThread().getName() + " baja del transporte en la terminal "
                    + cadenaTerminal(numeroTerminal));
            pasajerosDeTerminal[numeroTerminal - 1].acquire();
            maximoPasajeros.release();
        } else {
            System.out.println(Thread.currentThread().getName() + " espera su parada para bajar del transporte");
        }
        mutex.release();
    }

    public void llegadaParada(int parada) {
        try {
            mutex.acquire();
            this.paradaTerminal = parada;
        } catch (Exception e) {
            // TODO: handle exception
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
