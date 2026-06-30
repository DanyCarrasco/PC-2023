package tpObligatorio;

import java.util.concurrent.Semaphore;

public class Terminal {
    private String id;
    public FreeShop tienda;
    public SalaEmbarque sala;
    private int puestoEmbarqueInicial, puestoEmbarqueFinal, cantPasajeros;

    private Semaphore mutex;

    public Terminal(String id, int cantMaxima, int puestoEmbarqueInicial, int puestoEmbarqueFinal){
        this.id = id;
        this.puestoEmbarqueInicial = puestoEmbarqueInicial;
        this.puestoEmbarqueFinal = puestoEmbarqueFinal;
        this.cantPasajeros = 0;
        sala = new SalaEmbarque(id, cantPasajeros);
        tienda = new FreeShop(id, cantMaxima);
        mutex = new Semaphore(1);
    }
    
    public void ingresarTerminal(){
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cantPasajeros++;
        mutex.release();
    }

    
    
}
