package tp03.punto3;

public class Rueda {
    private boolean disponible;
    public Rueda(){
        this.disponible = false;
    }

    public void tomarRueda(String nombre){
        this.disponible = false;
        System.out.println(nombre + " usa rueda");
    }

    public void dejarRueda(String nombre){
        this.disponible = true;
        System.out.println(nombre + " deja rueda");
    }

    public boolean estaDisponible(){
        return this.disponible;
    }
}
