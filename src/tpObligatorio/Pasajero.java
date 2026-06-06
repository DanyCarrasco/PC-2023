package tpObligatorio;

public class Pasajero implements Runnable {
    private String[] boletoAvion;
    private String[] boletoTerminal;
    private PuestoAtencion puesto;
    private FreeShop tienda;
    private boolean comprar;

    public Pasajero(PuestoAtencion puesto, FreeShop tienda, boolean comprar) {
        this.boletoAvion = new String[1];
        boletoAvion[0] = "Compañia 1";
        this.boletoTerminal = new String[0];
        this.puesto = puesto;
        this.tienda = tienda;
        this.comprar = comprar;
    }

    public void run() {
        long tiempoRestante = 30000;
        long tiempoMaxEspera = tiempoRestante - 15000;
        try {
            // Primero va al puesto de atencion de su aerolinea con su boleto de Avion
            puesto.puedeEntrarPuesto();
            Thread.sleep(5000);
            puesto.puedeEntrarPuesto();
            boletoTerminal = puesto.realizarIntercambio(boletoAvion);
            if (boletoTerminal.length == 0) {
                System.out.println("Error de " + Thread.currentThread().getName() + ": el boleto no tiene ningun dato");
            } else {
                System.out.println(Thread.currentThread().getName() + " debe ir a la terminal "
                        + boletoTerminal[0] + ", en el puesto de embarque " + boletoTerminal[1]);
            }
            puesto.salirPuesto();

            // Ingresa a transporte publico y se dirige a la terminal, lo que le consume 15 segundos
            
            // En la terminal, intenta entrar al Free Shop con su boleto de Terminal
            /*if(tienda.ingresarFreeShop(tiempoMaxEspera)){
                if(comprar){
                    tienda.comprarEnFreeShop();
                } else {
                    tienda.salirFreeShop();
                }
            } else {
                System.out.println(Thread.currentThread().getName()+ " no pudo entrar porque no habia lugar en tiempo maximo permitido");
            }*/
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
