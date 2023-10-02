package tp03.punto3;

public class Plato {
    private boolean disponible;
    public Plato(){
        this.disponible = true;
    }

    public synchronized boolean tomarPlato(String nombre){
        boolean exito = false;
        if (this.disponible) {
            this.ocupaPlato();
            exito = true;
            System.out.println(nombre + " toma plato");
        } else {
            System.out.println(nombre + " no toma plato");
        }
        return exito;
    }

    public synchronized void soltarPlato(String nombre){
        this.disponible = true;
        System.out.println(nombre + " deja plato");
    }

    private void ocupaPlato(){
        this.disponible = false;
    }
}
