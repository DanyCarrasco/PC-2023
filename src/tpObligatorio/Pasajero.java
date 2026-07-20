package tpObligatorio;

public class Pasajero implements Runnable {
    private String[] boletoAvion;
    private String[] boletoTerminal;
    private PuestoAtencion puesto;
    private boolean comprar;
    private Aeropuerto aeropuerto;
    private ControlAeropuerto control;

    public Pasajero(ControlAeropuerto control, Aeropuerto aeropuerto, boolean comprar) {
        this.boletoAvion = new String[1];
        boletoAvion[0] = "Compañia 1";
        this.boletoTerminal = new String[0];
        this.aeropuerto = aeropuerto;
        this.control = control;
        this.comprar = comprar;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " intenta ingresar al aeropuerto VIAJE BONITO");
        boolean entro = control.entrarAlAeropuerto();
            //Si entra al aeropuerto, se dirige a un puesto de atencion de una aerolinea
            if (entro) {
                puesto = aeropuerto.entrada.informe.llegarAInforme();
            // Si el puesto de atencion no es nulo, se procede a realizar el intercambio de boleto y continua
            if (puesto != null) {
                try {
                    puesto.puedeEntrarPuesto();

                    boletoTerminal = puesto.realizarIntercambio(boletoAvion);

                    puesto.salirPuesto();

                    // Si el boletoTerminal tiene datos, se procede a ir a la terminal correspondiente
                    if (boletoTerminal.length == 0) {
                        System.out.println(
                                "Error de " + Thread.currentThread().getName() + ": el boleto no tiene ningun dato");
                    } else {
                        System.out.println(Thread.currentThread().getName() + " debe ir a la terminal "
                                + boletoTerminal[0] + ", en el puesto de embarque " + boletoTerminal[1]);

                        int terminal = numeroTerminal();
                        if (terminal >= 1 && terminal <= aeropuerto.terminales.length) {
                            int idxTerminal = terminal - 1; // Ajuste para índice de arreglo (0-based)
                            aeropuerto.transporte.subirATransporte(terminal);
                            aeropuerto.transporte.bajarDelTransporte(terminal);

                            // Se intenta ingresar al Free Shop de la terminal correspondiente, con un tiempo máximo de espera
                            long tiempoRestante = 30000;
                            long tiempoMaxEspera = tiempoRestante - 15000;
                            if (aeropuerto.terminales[idxTerminal].tienda.ingresarFreeShop(tiempoMaxEspera)) {
                                System.out.println(Thread.currentThread().getName()
                                        + " entra al Free Shop de la terminal " + boletoTerminal[0]);
                                if (comprar) {
                                    aeropuerto.terminales[idxTerminal].tienda.comprarEnFreeShop();
                                }
                                aeropuerto.terminales[idxTerminal].tienda.salirFreeShop();
                            } else {
                                System.out.println(Thread.currentThread().getName()
                                        + " no pudo entrar al Free Shop (sin tiempo suficiente o lleno)");
                            }

                            // Se espera el llamado de la sala de embarque de la terminal correspondiente
                            aeropuerto.terminales[idxTerminal].sala.esperarLlamado();
                        } else {
                            // Se maneja el caso de terminal inválida
                            System.out.println(Thread.currentThread().getName() + " terminal inválida: " + terminal);
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " fue interrumpido: " + e.getMessage());
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + " error: " + e.getMessage());
                }
            } else {
                // Si el puesto de atencion es nulo, se indica que no pudo ser derivado a un puesto de atencion de una aerolinea
                System.out.println(Thread.currentThread().getName()
                        + " no pudo ser derivado a un puesto de atencion de una aerolinea");
            }
        } else {
            // Si no entra al aeropuerto, se indica que no pudo ingresar porque está cerrado
            System.out.println(Thread.currentThread().getName() + " no pudo ingresar al aeropuerto porque esta CERRADO"
            );
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
