package tpObligatorio;

import java.util.concurrent.CountDownLatch;

public class SalaEmbarque {
    private String nombreTerminal;
    private final CountDownLatch embarque;

    public SalaEmbarque(String nombreTerminal, int cantPasajeros) {
        this.nombreTerminal = nombreTerminal;
        this.embarque = new CountDownLatch(cantPasajeros);
    }

    public void esperarLlamado() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()
                + " espera en la sala de embarque el llamado a embarcar");
        embarque.countDown();
        embarque.await();
        System.out.println(Thread.currentThread().getName()
                + " escucha el llamado a embarcar y sube al avion");
    }

    public void llamarAEmbarcar() {
        System.out.println("*** " + Thread.currentThread().getName() + " LLAMA A EMBARCAR A PASAJEROS DEL TERMINAL "
                + nombreTerminal + "***");
        while (embarque.getCount() > 0) {
            embarque.countDown();
        }
    }
}
