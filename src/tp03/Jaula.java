package tp03;

public class Jaula {
    private Plato plato;
    private Rueda rueda;
    private Hamaca hamaca;

    public Jaula(){
        plato = new Plato();
        rueda = new Rueda();
        hamaca = new Hamaca();
    }

    public synchronized boolean usarPlato(String nombre) {
        boolean hecho = false;
        if (plato.estaDisponible()) {
            plato.tomarPlato(nombre);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            plato.soltarPlato(nombre);
            hecho = true;
        }
        return hecho;
    }

    public synchronized boolean usarRueda(String nombre) {
        boolean hecho = false;
        if (rueda.estaDisponible()) {
            rueda.tomarRueda(nombre);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rueda.dejarRueda(nombre);
            hecho = true;
        }
        return hecho;
    }

    public synchronized boolean usarHamaca(String nombre) {
        boolean hecho = false;
        if (hamaca.estaDisponible()) {
            hamaca.tomarHamaca(nombre);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hamaca.dejarHamaca(nombre);
            hecho = true;
        }
        return hecho;
    }
}
