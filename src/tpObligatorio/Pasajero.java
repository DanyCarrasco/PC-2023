package tpObligatorio;

public class Pasajero implements Runnable {
    private String[] boletoAvion;
    private String[] boletoTerminal;
    private PuestoAtencion puesto;
    private TransporteATerminal transporte;
    private FreeShop tienda;
    private SalaEmbarque salaEmbarque;
    private boolean comprar;

    public Pasajero(PuestoAtencion puesto, TransporteATerminal transporte,
                    FreeShop tienda, SalaEmbarque salaEmbarque, boolean comprar) {
        this.boletoAvion = new String[1];
        boletoAvion[0] = "Compañia 1";
        this.boletoTerminal = new String[0];
        this.puesto = puesto;
        this.transporte = transporte;
        this.tienda = tienda;
        this.salaEmbarque = salaEmbarque;
        this.comprar = comprar;
    }

    public void run() {
        boolean ingreso = false;
        try {
            puesto.puedeEntrarPuesto();
            ingreso = true;

            boletoTerminal = puesto.realizarIntercambio(boletoAvion);
            if (boletoTerminal.length == 0) {
                System.out.println("Error de " + Thread.currentThread().getName() + ": el boleto no tiene ningun dato");
            } else {
                System.out.println(Thread.currentThread().getName() + " debe ir a la terminal "
                        + boletoTerminal[0] + ", en el puesto de embarque " + boletoTerminal[1]);

                int terminal = numeroTerminal();
                if (terminal >= 1 && terminal <= 26) {
                    transporte.subirATransporte(terminal);
                    transporte.bajarDelTransporte(terminal);

                    long tiempoRestante = 30000;
                    long tiempoMaxEspera = tiempoRestante - 15000;
                    if (tienda.ingresarFreeShop(tiempoMaxEspera)) {
                        System.out.println(Thread.currentThread().getName()
                                + " entra al Free Shop de la terminal " + boletoTerminal[0]);
                        if (comprar) {
                            tienda.comprarEnFreeShop();
                        }
                        tienda.salirFreeShop();
                    } else {
                        System.out.println(Thread.currentThread().getName()
                                + " no pudo entrar al Free Shop (sin tiempo suficiente o lleno)");
                    }

                    salaEmbarque.esperarLlamado();
                } else {
                    System.out.println(Thread.currentThread().getName() + " terminal inválida: " + terminal);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " error: " + e.getMessage());
        } finally {
            if (ingreso) {
                puesto.salirPuesto();
            }
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
