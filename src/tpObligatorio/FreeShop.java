package tpObligatorio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class FreeShop {
    private String idTerminal;

    private final ArrayBlockingQueue<Thread> capacidad;

    private final Semaphore lugar;

    private final Semaphore avisoPasajero;
    private final Semaphore avisoCajero;

    private boolean pagoPendiente;
    private boolean pagoCompletado;

    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore turnoSalida = new Semaphore(0);
    private int esperandoTurno = 0;

    public FreeShop(String idTerminal, int lugar) {
        this.idTerminal = idTerminal;
        this.capacidad = new ArrayBlockingQueue<>(lugar);
        this.lugar = new Semaphore(lugar, true);
        this.avisoPasajero = new Semaphore(0);
        this.avisoCajero = new Semaphore(0);
        this.pagoPendiente = false; // Indica si hay un pasajero esperando para pagar
        this.pagoCompletado = false; // Indica si el pago ha sido completado por el cajero
    }

    // Pasajero intenta ingresar al Free Shop
    public boolean ingresarFreeShop(long tiempoMaxEspera) {
        try {
            // Intenta adquirir un lugar en el Free Shop con un tiempo máximo de espera
            if (!lugar.tryAcquire(tiempoMaxEspera, TimeUnit.MILLISECONDS)) {
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        try {
            capacidad.put(Thread.currentThread()); // Agrega el hilo actual a la cola de capacidad
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            lugar.release();
            return false;
        }
        return true;
    }

    // Pasajero compra en el Free Shop
    public void comprarEnFreeShop() {
        System.out.println(Thread.currentThread().getName() + " compra en Free Shop");
        System.out.println(Thread.currentThread().getName() + " avisa a los cajeros que quiere pagar");

        try {
            mutex.acquire();
            pagoPendiente = true;
            pagoCompletado = false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();
        }

        avisoPasajero.release();

        try {
            avisoCajero.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        System.out.println(Thread.currentThread().getName() + " paga y se lleva su producto");
    }

    // Pasajero sale del Free Shop
    public void salirFreeShop() throws InterruptedException {
        Thread yo = Thread.currentThread();
        mutex.acquire();
        if (capacidad.peek() != yo) {
            System.out.println(Thread.currentThread().getName()
                    + " espera su turno para salir del Free Shop de la terminal " + idTerminal);
            esperandoTurno++;
            mutex.release();
            turnoSalida.acquire();
            mutex.acquire();
            esperandoTurno--;
        }
        capacidad.take();
        if (esperandoTurno > 0) {
            turnoSalida.release();
        }
        mutex.release();
        lugar.release();
        System.out.println(Thread.currentThread().getName()
                + " mira los productos y sale del Free Shop de la terminal " + idTerminal);
    }

    // Cajero procesa el pago del pasajero
    public void procesarPago() {
        try {
            avisoPasajero.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        try {
            mutex.acquire();
            System.out.println(Thread.currentThread().getName()
                    + " recibe aviso y recibe el pago del producto del pasajero");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();
        }
    }

    // Cajero entrega el ticket de compra al pasajero
    public void entregarTicketCompra() {
        try {
            mutex.acquire();
            pagoPendiente = false;
            pagoCompletado = true;
            System.out.println(Thread.currentThread().getName()
                    + " entrega el ticket de la compra al pasajero");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();
        }

        avisoCajero.release();
    }
}
