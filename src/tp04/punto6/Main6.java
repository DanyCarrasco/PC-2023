package punto6;

public class Main6 {
    public static void main(String[] args) {
        Taxi taxi = new Taxi();
        Pasajero p = new Pasajero(taxi);
        Taxista t = new Taxista(taxi);
        Thread hiloP = new Thread(p,"Pasajero");
        Thread hiloT = new Thread(t, "Taxista");
        hiloP.start();
        hiloT.start();
    }
}
