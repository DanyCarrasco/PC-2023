package tp03.punto3;

public class Jaula {
    private Plato plato;
    private Rueda rueda;
    private Hamaca hamaca;

    public Jaula() {
        plato = new Plato();
        rueda = new Rueda();
        hamaca = new Hamaca();
    }

    public synchronized boolean usarPlato(String nombre) {
        boolean exito = false;
        if (plato.estaDisponible()) {
            plato.tomarPlato(nombre);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            plato.soltarPlato(nombre);
            exito = true;
        }
        return exito;
    }

    public synchronized boolean usarRueda(String nombre) {
        boolean exito = false;
        if (rueda.estaDisponible()) {
            rueda.tomarRueda(nombre);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rueda.dejarRueda(nombre);
            exito = true;
        }
        return exito;
    }

    public synchronized boolean usarHamaca(String nombre) {
        boolean exito = false;
        if (hamaca.estaDisponible()) {
            hamaca.tomarHamaca(nombre);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hamaca.dejarHamaca(nombre);
            exito = true;
        }
        return exito;
    }
}
