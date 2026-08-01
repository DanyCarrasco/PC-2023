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
                Vuelo vuelo = embarques.sala.llamarAEmbarcar();
                new Thread(vuelo, vuelo.getNombreVuelo()).start();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
