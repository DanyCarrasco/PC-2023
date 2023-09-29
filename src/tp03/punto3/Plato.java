package tp03.punto3;

public class Plato {
    private boolean disponible;
    public Plato(){
        this.disponible = true;
    }

    public void tomarPlato(String nombre){
        this.disponible = false;
        System.out.println(nombre + " toma plato");
    }

    public void soltarPlato(String nombre){
        this.disponible = true;
        System.out.println(nombre + " deja plato");
    }

    public boolean estaDisponible(){
        return this.disponible;
    }

}
