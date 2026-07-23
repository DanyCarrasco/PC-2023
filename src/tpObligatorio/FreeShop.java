package tpObligatorio;

public class FreeShop {
    private String idTerminal;
    private int capacidadMax;
    private int contador;
    private int esperandoTurno;
    private int turnoActual;
    private boolean pagoPendiente;

    public FreeShop(String idTerminal, int capacidad) {
        this.idTerminal = idTerminal;
        this.capacidadMax = capacidad;
        this.contador = 0;
        this.esperandoTurno = 0;
        this.turnoActual = 0;
        this.pagoPendiente = false;
    }

    public synchronized boolean ingresarFreeShop(long tiempoMaxEspera) {
        boolean ingresoExitoso = true;;
        long tiempoLimite = System.currentTimeMillis() + tiempoMaxEspera;
        while (contador >= capacidadMax) {
            long restante = tiempoLimite - System.currentTimeMillis();
            if (restante <= 0) {
                ingresoExitoso = false;
            }
            try {
                wait(restante);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                ingresoExitoso = false;
            }
        }
        contador++;
        System.out.println(Thread.currentThread().getName()
                + " entra al Free Shop de la terminal " + idTerminal);
        return ingresoExitoso;
    }

    public synchronized void comprarEnFreeShop() {
        System.out.println(Thread.currentThread().getName() + " compra en Free Shop");
        System.out.println(Thread.currentThread().getName()
                + " avisa a los cajeros que quiere pagar");
        pagoPendiente = true;
        notifyAll();
        while (pagoPendiente) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println(Thread.currentThread().getName()
                + " paga y se lleva su producto");
    }

    public synchronized void salirFreeShop() throws InterruptedException {
        int miTurno = esperandoTurno;
        esperandoTurno++;
        while (turnoActual != miTurno) {
            System.out.println(Thread.currentThread().getName()
                    + " espera su turno para salir del Free Shop de la terminal " + idTerminal);
            wait();
        }
        turnoActual++;
        contador--;
        notifyAll();
        System.out.println(Thread.currentThread().getName()
                + " mira los productos y sale del Free Shop de la terminal " + idTerminal);
    }

    public synchronized void procesarPago() {
        while (!pagoPendiente) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println(Thread.currentThread().getName()
                + " recibe aviso y recibe el pago del producto del pasajero");
    }

    public synchronized void entregarTicketCompra() {
        if (pagoPendiente) {
            pagoPendiente = false;
            System.out.println(Thread.currentThread().getName()
                    + " entrega el ticket de la compra al pasajero");
            notifyAll();
        }
    }
}
