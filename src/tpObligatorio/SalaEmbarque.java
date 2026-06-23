package tpObligatorio;

import java.util.concurrent.CountDownLatch;

public class SalaEmbarque {
    private final CountDownLatch embarque;

    public SalaEmbarque(int cantPasajeros) {
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
        System.out.println("*** LA TORRE DE CONTROL LLAMA A EMBARCAR ***");
        while (embarque.getCount() > 0) {
            embarque.countDown();
        }
    }
}
