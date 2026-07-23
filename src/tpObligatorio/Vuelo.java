package tpObligatorio;

public class Vuelo implements Runnable {
    private Aeropuerto aeropuerto;

    public Vuelo(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    public void run() {
        try {
            aeropuerto.avionDespega.await();
                System.out.println("=== EL AVION DESPEGA CON TODOS LOS PASAJEROS A BORDO ===");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
}
