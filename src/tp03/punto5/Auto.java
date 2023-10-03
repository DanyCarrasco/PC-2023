package tp03.punto5;

public class Auto extends Vehiculo implements Runnable{
    private String marca;
    private String modelo;
    private String patente;
    private int km;
    public Auto(String marca, String modelo, String patente, int km){
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.km = km;
    }

    public void run(){
        System.out.println("Auto: " + this.marca + " " + this.modelo + " " + this.patente + " " + this.km + "km");
        System.out.println("Auto: "+ this.marca +". Listo para salir.");
        System.out.println("Auto: "+ this.marca +". Arrancando.");
        
    }
}
