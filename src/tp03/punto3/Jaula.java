package tp03.punto3;

public class Jaula {
    private Plato plato;
    private Rueda rueda;
    private Hamaca hamaca;

    public Jaula(){
        plato = new Plato();
        rueda = new Rueda();
        hamaca = new Hamaca();
    }

    public void usarPlato(String nombre){
        plato.tomarPlato(nombre);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        plato.soltarPlato(nombre);
    }

    public void usarRueda(String nombre){
        rueda.tomarRueda(nombre);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rueda.dejarRueda(nombre);
    }

    public void usarHamaca(String nombre){
        hamaca.tomarHamaca(nombre);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hamaca.dejarHamaca(nombre);
    }
}
