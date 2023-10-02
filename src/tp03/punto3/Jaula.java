package tp03.punto3;

public class Jaula {
    private Plato plato;
    /*private Rueda rueda;
    private Hamaca hamaca;*/

    public Jaula(){
        plato = new Plato();
        /*rueda = new Rueda();
        hamaca = new Hamaca();*/
    }

    public synchronized boolean tomaPlato(String nombre){
        return plato.tomarPlato(nombre);
    }

    public synchronized void dejaPlato(String nombre){
        plato.soltarPlato(nombre);
    }

    /*public synchronized void tomaRueda(String nombre){
        rueda.tomarRueda(nombre);
    }

    public synchronized void dejaRueda(String nombre){
        rueda.dejarRueda(nombre);
    }

    public synchronized void tomaHamaca(String nombre){
        hamaca.tomarHamaca(nombre);
    }

    public synchronized void dejaHamaca(String nombre){
        hamaca.dejarHamaca(nombre);
    }*/
}
