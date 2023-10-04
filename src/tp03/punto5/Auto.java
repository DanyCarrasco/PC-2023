package tp03.punto5;

import java.util.Random;

public class Auto extends Vehiculo implements Runnable {
    //private String marca;
    //private String modelo;
    private String patente;
    private int topeKm;
    private Surtidor surtidor;

    public Auto(/*String marca, String modelo,*/ String patente, int topeKm, Surtidor surtidor) {
        //this.marca = marca;
        //this.modelo = modelo;
        this.patente = patente;
        this.topeKm = topeKm;
        this.surtidor = surtidor;
    }

    public void run() {
        Random rand = new Random();
        int numAleatorio = rand.nextInt(topeKm) + 1;
        int i = 0;
        //System.out.println("Auto: "+ this.patente);
        System.out.println("Auto: " + this.patente + ". Listo para salir con " + this.topeKm + " km maximo.");
        System.out.println("Auto: " + this.patente + ". Arrancando.");
        while (numAleatorio <= topeKm) {
            System.out.println("Auto " + this.patente + " esta recorriendo la cuidad con " + numAleatorio + " km recorridos.");
            numAleatorio++;
            i++;
        }
        System.out.println("Auto " + this.patente + " llega al km maximo de consumo. Va al surtidor para abastecerse de combustible");
        if (surtidor.cargarCombustible(i)) {
            System.out.println("Auto " + this.patente + " pudo cargar " + i + " litros combusible en el surtidor");
        } else {
            System.out.println("Auto " + this.patente + " no pudo cargar " + i + " litros combusible en el surtidor");
        }
        System.out.println("Auto " + this.patente + " termina su recorrido");
    }

    /*private void tiempo(){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/
}
