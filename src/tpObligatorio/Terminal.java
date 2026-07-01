package tpObligatorio;

import java.util.concurrent.Semaphore;

public class Terminal {
    private String id;
    public FreeShop tienda;
    public SalaEmbarque sala;
    private int cantPasajeros;
    private int[] cantidadPuertos = new int[2]; // puertos de embarque

    private Semaphore mutex;

    public Terminal(String id, int cantMaxima, int puertoInicio, int puertoFinal){
        this.id = id;
        this.cantPasajeros = 0;
        sala = new SalaEmbarque(id);
        tienda = new FreeShop(id, cantMaxima);
        mutex = new Semaphore(1);
        cantidadPuertos[0] = puertoInicio;
        cantidadPuertos[1] = puertoFinal;
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
    
    public String getId(){
        return id;
    }

    public int getPuertoInicio(){
        return this.cantidadPuertos[0];
    }

    public int getPuertoFinal(){
        return this.cantidadPuertos[1];
    }
}
