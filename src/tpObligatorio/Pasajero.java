package tpObligatorio;

public class Pasajero implements Runnable {
    private String[] boletoAvion;
    private String[] boletoTerminal;
    private PuestoAtencion puesto;

    private TransporteATerminal transporte;

    private FreeShop tienda;
    private boolean comprar;

    public Pasajero(PuestoAtencion puesto, TransporteATerminal transporte, FreeShop tienda, boolean comprar) {
        this.boletoAvion = new String[1];
        boletoAvion[0] = "Compañia 1";
        this.boletoTerminal = new String[0];
        this.puesto = puesto;

        this.transporte = transporte;

        this.tienda = tienda;
        this.comprar = comprar;
    }

    public void run() {
        // long tiempoRestante = 30000;
        // long tiempoMaxEspera = tiempoRestante - 15000;
        try {
            // Primero va al puesto de atencion de su aerolinea con su boleto de Avion
            puesto.puedeEntrarPuesto();
            puesto.permitirIngreso();
            System.out.println(Thread.currentThread().getName() + " ingresa al puesto de atencion");

            // 2. Realizar intercambio de boleto
            boletoTerminal = puesto.realizarIntercambio(boletoAvion);
            if (boletoTerminal.length == 0) {
                System.out.println("Error de " + Thread.currentThread().getName() + ": el boleto no tiene ningun dato");
                return;
            }
            System.out.println(Thread.currentThread().getName() + " debe ir a la terminal "
                    + boletoTerminal[0] + ", en el puesto de embarque " + boletoTerminal[1]);

            // 3. Salir del puerto (libera permiso)
            puesto.salirPuesto();

            // 4. Tomar transporte a la terminal
            int terminal = numeroTerminal();
            if (terminal < 1 || terminal > 26) {
                System.out.println(Thread.currentThread().getName() + " terminal inválida: " + terminal);
                return;
            }

            transporte.subirATransporte(terminal);
            transporte.bajarDelTransporte(terminal);

            // En la terminal, intenta entrar al Free Shop con su boleto de Terminal
            /*
             * if(tienda.ingresarFreeShop(tiempoMaxEspera)){
             * if(comprar){
             * tienda.comprarEnFreeShop();
             * } else {
             * tienda.salirFreeShop();
             * }
             * } else {
             * System.out.println(Thread.currentThread().getName()+
             * " no pudo entrar porque no habia lugar en tiempo maximo permitido");
             * }
             */
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " error: " + e.getMessage());
        }
    }

    private int numeroTerminal() throws IllegalArgumentException {
        if (boletoTerminal.length == 0 || boletoTerminal[0] == null || boletoTerminal[0].isEmpty()) {
            throw new IllegalArgumentException("Boleto sin terminal");
        }
        char letra = boletoTerminal[0].charAt(0);
        if (letra < 'A' || letra > 'Z') {
            throw new IllegalArgumentException("Terminal inválida: " + letra);
        }
        return (letra - 'A') + 1;
    }

}
