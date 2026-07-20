package tpObligatorio;

public class FreeShop {
    private String idTerminal;
    private int capacidadMax;
    private int contador;        // pasajeros actualmente dentro
    private int esperandoTurno;   // pasajeros esperando para salir (FIFO)
    private boolean pagoPendiente;
    private int notificaciones;   // contador de seniales enviadas (para detectar timeouts)

    public FreeShop(String idTerminal, int capacidad) {
        this.idTerminal = idTerminal;
        this.capacidadMax = capacidad;
        this.contador = 0;
        this.esperandoTurno = 0;
        this.pagoPendiente = false;
        this.notificaciones = 0;
    }

    // Pasajero intenta ingresar al Free Shop con tiempo maximo de espera.
    // Usa synchronized + wait(millis) para simular tryAcquire con timeout.
    public boolean ingresarFreeShop(long tiempoMaxEspera) {
        synchronized (this) {
            long deadline = System.currentTimeMillis() + tiempoMaxEspera;
            int notifAntes = notificaciones;

            while (contador >= capacidadMax) {
                long restante = deadline - System.currentTimeMillis();
                if (restante <= 0) {
                    // Timeout: verificar si fue por senial o por tiempo.
                    // Si notificaciones no cambio, fue timeout real → no entrar.
                    if (notificaciones == notifAntes) {
                        return false;
                    }
                    // Fue una senial, reintentar
                    notifAntes = notificaciones;
                    continue;
                }
                try {
                    wait(restante);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
            contador++;
            System.out.println(Thread.currentThread().getName()
                    + " entra al Free Shop de la terminal " + idTerminal);
            return true;
        }
    }

    // Pasajero compra en el Free Shop.
    // Handoff con cajero via wait/notifyAll (monitor).
    public void comprarEnFreeShop() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " compra en Free Shop");
            System.out.println(Thread.currentThread().getName()
                    + " avisa a los cajeros que quiere pagar");
            pagoPendiente = true;
            notificaciones++;
            notifyAll(); // avisar al cajero

            // Esperar a que el cajero complete el pago
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
    }

    // Pasajero sale del Free Shop.
    // FIFO: solo sale si es el primero en haber entrado.
    public void salirFreeShop() throws InterruptedException {
        synchronized (this) {
            contador--; // sale del shop
            if (contador > 0) {
                // Hay otros dentro: esperar turno
                System.out.println(Thread.currentThread().getName()
                        + " espera su turno para salir del Free Shop de la terminal " + idTerminal);
                while (esperandoTurno > 0) {
                    wait();
                }
            }
            // Soy el primero (o el unico): salgo y libero el turno
            esperandoTurno++;
            notificaciones++;
            notifyAll(); // despertar al siguiente
            esperandoTurno--;
            if (esperandoTurno > 0) {
                notifyAll(); // avisar al siguiente que ya puede salir
            }
        }
        System.out.println(Thread.currentThread().getName()
                + " mira los productos y sale del Free Shop de la terminal " + idTerminal);
    }

    // Cajero espera a que un pasajero quiera pagar (handoff via monitor).
    public void procesarPago() {
        synchronized (this) {
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
    }

    // Cajero entrega el ticket y seniala al pasajero que el pago se completo.
    public void entregarTicketCompra() {
        synchronized (this) {
            pagoPendiente = false;
            System.out.println(Thread.currentThread().getName()
                    + " entrega el ticket de la compra al pasajero");
            notificaciones++;
            notifyAll(); // despertar al pasajero que esta esperando
        }
    }
}
