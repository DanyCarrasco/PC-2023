package tpObligatorio;

import java.util.concurrent.CountDownLatch;

public class SalaEmbarque {
    private String nombreTerminal;
    private final CountDownLatch embarque;
    private final CountDownLatch pasajeroPresente;
    private boolean llamadoRealizado = false;

    public SalaEmbarque(String nombreTerminal) {
        this.nombreTerminal = nombreTerminal;
        this.embarque = new CountDownLatch(1);
        this.pasajeroPresente = new CountDownLatch(1);
    }

    public void esperarLlamado() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()
                + " espera en la sala de embarque el llamado a embarcar");
        pasajeroPresente.countDown();
        embarque.await();
        System.out.println(Thread.currentThread().getName()
                + " escucha el llamado a embarcar y sube al avion");
    }

    public void llamarAEmbarcar() {
        if (!llamadoRealizado) {
            System.out.println("*** " + Thread.currentThread().getName() + " LLAMA A EMBARCAR A PASAJEROS DEL TERMINAL "
                    + nombreTerminal + "***");
            llamadoRealizado = true;
            embarque.countDown();
        }
    }

    public void esperarPasajero() throws InterruptedException {
        pasajeroPresente.await();
    }
}
