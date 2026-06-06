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

    private CyclicBarrier barrera;
    private Runnable accionInicio;
    private Semaphore mutex;

    private String parada = "";

    public TransporteATerminal(int cantidad) {
        this.accionInicio = avisarConductor();
        this.barrera = new CyclicBarrier(cantidad, accionInicio);
    }

    public void subirATransporte() {
        try {
            System.out.println(Thread.currentThread().getName() + " sube al transporte");
            barrera.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bajarDelTransporte(String terminal){
        // cuando el conducto avisa que llego a su parada, se baja
        if (parada.equals(terminal)) {
            System.out.println(Thread.currentThread().getName() + " baja del transporte en la terminal " + terminal);
        } else {
            System.out.println(Thread.currentThread().getName() + " espera su parada para bajar del transporte");
        }
    }

    private Runnable avisarConductor() {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("El conductor avisa que llegaron a la terminal");
            }
        };
    }
}
