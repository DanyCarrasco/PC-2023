package tp03.punto3;

public class Jaula {
    //private boolean plato; // rueda,hamaca;
    /*
     * private Plato plato;
     * private Rueda rueda;
     * private Hamaca hamaca;
     */

    public Jaula() {
        //this.plato = true;
        /*
         * this.rueda = true;
         * this.hamaca = true;
         */
        /*
         * plato = new Plato();
         * rueda = new Rueda();
         * hamaca = new Hamaca();
         */
    }

    public synchronized void usarPlato(String nombre) {
        System.out.println(nombre + " toma plato");
        tiempo();
        System.out.println(nombre + " deja plato");
    }

    public synchronized void usarRueda(String nombre) {
        System.out.println(nombre + " toma rueda");
        tiempo();
        System.out.println(nombre + " deja rueda");
    }

    public synchronized void usarHamaca(String nombre) {
        System.out.println(nombre + " toma hamaca");
        tiempo();
        System.out.println(nombre + " deja hamaca");
    }

    /*
     * public synchronized boolean usarRueda(String nombre) {
     * boolean exito = false;
     * if (rueda.estaDisponible()) {
     * rueda.tomarRueda(nombre);
     * 
     * rueda.dejarRueda(nombre);
     * exito = true;
     * }
     * return exito;
     * }
     * 
     * public synchronized boolean usarHamaca(String nombre) {
     * boolean exito = false;
     * if (hamaca.estaDisponible()) {
     * hamaca.tomarHamaca(nombre);
     * 
     * hamaca.dejarHamaca(nombre);
     * exito = true;
     * }
     * return exito;
     * }
     */

    private void tiempo() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
