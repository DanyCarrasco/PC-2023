package tpObligatorio;

public class EmpleadoSalon implements Runnable{
    // Avisa a los pasajeros que estan esperando en una terminal

    private Terminal embarques;

    public EmpleadoSalon(Terminal embarques){
        this.embarques = embarques;
    }

    public void run(){
        while (true) {
            embarques.sala.llamarAEmbarcar();
        }
    }    
}
