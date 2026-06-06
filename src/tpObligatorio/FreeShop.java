package tpObligatorio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class FreeShop {
    private String idTerminal;

    // Identidad: registra qué pasajeros están adentro (FIFO)
    private final ArrayBlockingQueue<Thread> capacidad;

    // Concurrencia: N permisos = N pasajeros simultáneos
    private final Semaphore lugar;

    // Señalización entre pasajeros y cajeros
    private final Semaphore avisoPasajero; // pasajero -> cajero
    private final Semaphore avisoCajero; // cajero -> pasajero

    // Estado protegido por el propio Semaphore como mutex (seAcquire(1) = lock)
    private final Semaphore mutex = new Semaphore(1, true);
    private boolean pagoPendiente = false;
    private boolean pagoCompletado = false;

    public FreeShop(String idTerminal, int lugar) {
        this.idTerminal = idTerminal;
        this.capacidad = new ArrayBlockingQueue<>(lugar);
        this.lugar = new Semaphore(lugar, true);
        this.avisoPasajero = new Semaphore(0);
        this.avisoCajero = new Semaphore(0);
    }

    // -------- PASAJERO: intenta entrar al Free Shop --------
    public boolean ingresarFreeShop(long tiempoMaxEspera) {
        try {
            // Adquiere un permiso (con timeout si se especifica)
            if (!lugar.tryAcquire(tiempoMaxEspera, TimeUnit.MILLISECONDS)) {
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        // Una vez adentro, registra la identidad
        try {
            capacidad.put(Thread.currentThread());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            lugar.release(); // importante: devuelve el permiso si fue interrumpido
            return false;
        }
        return true;
    }

    // -------- PASAJERO: compra y paga --------
    public void comprarEnFreeShop() {
        System.out.println(Thread.currentThread().getName() + " compra en Free Shop");
        System.out.println(Thread.currentThread().getName() + " avisa a los cajeros que quiere pagar");

        try {
            mutex.acquire();
            pagoPendiente = true;
            pagoCompletado = false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        } finally {
            mutex.release();
        }

        // Avisa al cajero que hay un pago pendiente
        avisoPasajero.release();

        try {
            // Espera el ticket del cajero
            avisoCajero.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        System.out.println(Thread.currentThread().getName() + " paga y se lleva su producto");
    }

    // -------- PASAJERO: sale del Free Shop --------
    public void salirFreeShop() {
        // Valida que sea el pasajero correcto (FIFO)
        Thread yo = Thread.currentThread();
        Thread cabeza;
        try {
            cabeza = capacidad.peek();
        } catch (Exception e) {
            cabeza = null;
        }

        if (cabeza != yo) {
            // No es el turno de este pasajero: lo deja en la cola y retorna
            // (la cola mantiene el orden FIFO de salida)
            System.out.println(Thread.currentThread().getName()
                    + " espera su turno para salir del Free Shop de la terminal " + idTerminal);
            return;
        }

        try {
            capacidad.take(); // saca al pasajero actual
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        lugar.release(); // libera el permiso para el próximo pasajero
        System.out.println(Thread.currentThread().getName()
                + " mira los productos y sale del Free Shop de la terminal " + idTerminal);
    }

    // -------- CAJERO: procesa el pago --------
    public void procesarPago() {
        try {
            // Espera el aviso de un pasajero
            avisoPasajero.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        try {
            mutex.acquire();
            // Lectura del estado bajo mutex para consistencia
            System.out.println(Thread.currentThread().getName()
                    + " recibe aviso y recibe el pago del producto del pasajero");
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            return;
        } finally {
            mutex.release();
        }
    }

    // -------- CAJERO: entrega el ticket --------
    public void entregarTicketCompra() {
        boolean acquired = false;
        try {
            mutex.acquire();
            acquired = true;
            pagoPendiente = false;
            pagoCompletado = true;
            System.out.println(Thread.currentThread().getName()
                    + " entrega el ticket de la compra al pasajero");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        } finally {
            if (acquired) {
                mutex.release();
            }
        }

        // Despierta al pasajero
        avisoCajero.release();
    }
}