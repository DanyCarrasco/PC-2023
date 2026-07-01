package tpObligatorio;

import java.util.concurrent.Semaphore;

public class Terminal {
    private String id;
    public FreeShop tienda;
    public SalaEmbarque sala;
    private int cantPasajeros;

    private Semaphore mutex;

    public Terminal(String id, int cantMaxima){
        this.id = id;
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

    public void cambiarCantidadPasajeros(int cantidad){
        cantPasajeros = cantidad;
    }
    
    
}
