package punto6;

public class Pasajero implements Runnable {
    private Taxi taxi;
    // private boolean viajo;

    public Pasajero(Taxi taxi) {
        this.taxi = taxi;
        // this.viajo = false;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " esta caminando por la calle buscando un taxi");
        taxi.subirAlTaxi();
        System.out.println(Thread.currentThread().getName() + " encuentra un taxi");
        taxi.solicitarTaxi();
        taxi.esperarAviso();
        System.out.println(Thread.currentThread().getName() + " paga al taxista y deja el taxi");
        taxi.bajarDelTaxi();
    }
}
