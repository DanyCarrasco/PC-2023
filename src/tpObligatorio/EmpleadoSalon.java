package tpObligatorio;

public class EmpleadoSalon implements Runnable {
    private Terminal embarques;

    public EmpleadoSalon(Terminal embarques) {
        this.embarques = embarques;
    }

    public void run() {
        try {
            while (true) {
                embarques.sala.esperarPasajero();
                embarques.sala.llamarAEmbarcar();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
