package tp03.punto3;

public class Hamaca {
    private boolean disponible;
    public Hamaca(){
        this.disponible = true;
    }

    public void tomarHamaca(String nombre){
        this.disponible = false;
        System.out.println(nombre + " usa hamaca");
    }

    public void dejarHamaca(String nombre){
        this.disponible = true;
        System.out.println(nombre + " deja hamaca");
    }

    public boolean estaDisponible(){
        return this.disponible;
    }
}
