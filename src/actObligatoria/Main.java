package actObligatoria;

public class Main {
    public static void main(String[] args) {
        Tren tren = new Tren(2);
        Pasajero colPasajeros[] = new Pasajero[5];
        Thread hilosPasajeros[] = new Thread[5];
        ControlTren controlTren = new ControlTren(tren);
        Thread hiloControlTren = new Thread(controlTren, "controlTren");

        for (int i = 0; i < colPasajeros.length; i++) {
            //crear pasajeros
            colPasajeros[i] = new Pasajero(tren);
        }

        for (int i = 0; i < hilosPasajeros.length; i++) {
            //crear hilos de pasajeros
            hilosPasajeros[i] = new Thread(colPasajeros[i], "pasajero "+i);
        }

        for (int i = 0; i < hilosPasajeros.length; i++) {
            //lanzar hilos de pasajeros
            hilosPasajeros[i].start();
        }

        hiloControlTren.start();
    }
}
