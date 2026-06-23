package tpObligatorio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FreeShop {
    private String idTerminal;

    // Identidad: registra qué pasajeros están adentro (FIFO)
    private final ArrayBlockingQueue<Thread> capacidad;

    // Concurrencia: N permisos = N pasajeros simultáneos
    private final Semaphore lugar;

    // Señalización entre pasajeros y cajeros
    private final Semaphore avisoPasajero; // pasajero -> cajero
    private final Semaphore avisoCajero; // cajero -> pasajero

    // Lock (ReentrantLock) reemplaza al Semaphore como mutex para cumplir con la consigna
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition turnoSalida = lock.newCondition();
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

        lock.lock();
        try {
            pagoPendiente = true;
            pagoCompletado = false;
        } finally {
            lock.unlock();
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
    public void salirFreeShop() throws InterruptedException {
        Thread yo = Thread.currentThread();
        lock.lock();
        try {
            while (capacidad.peek() != yo) {
                System.out.println(Thread.currentThread().getName()
                        + " espera su turno para salir del Free Shop de la terminal " + idTerminal);
                turnoSalida.await();
            }
            capacidad.take();
            turnoSalida.signalAll();
        } finally {
            lock.unlock();
        }
        lugar.release();
        System.out.println(Thread.currentThread().getName()
                + " mira los productos y sale del Free Shop de la terminal " + idTerminal);
    }

    // -------- CAJERO: procesa el pago --------
    public void procesarPago() {
        try {
            avisoPasajero.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()
                    + " recibe aviso y recibe el pago del producto del pasajero");
        } finally {
            lock.unlock();
        }
    }

    // -------- CAJERO: entrega el ticket --------
    public void entregarTicketCompra() {
        lock.lock();
        try {
            pagoPendiente = false;
            pagoCompletado = true;
            System.out.println(Thread.currentThread().getName()
                    + " entrega el ticket de la compra al pasajero");
        } finally {
            lock.unlock();
        }

        avisoCajero.release();
    }
}