package tpObligatorio;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

public class PuestoAtencionS {
    private String nombre;
    private int cantidadTerminal;
    private int[][] tamanioPE;
    private Semaphore capacidad;        // controla maxPasajeros simultáneos
    private boolean intercambioEnCurso = false;
    private final Exchanger<String[]> exchanger = new Exchanger<>();
    private String[] boletoTerminal;

    public PuestoAtencionS(String nombre, int cantidadMaxima, int cantidadTerminales, int[][] tamanioPE) {
        this.nombre = nombre;
        this.cantidadTerminal = cantidadTerminales;
        this.tamanioPE = tamanioPE;
        this.capacidad = new Semaphore(cantidadMaxima, true);
        this.boletoTerminal = new String[2];
    }

    // Pasajero entra al puesto (espera si no hay lugar)
    public void entrarPuesto() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " espera lugar en el puesto de " + nombre);
        capacidad.acquire();
        System.out.println(Thread.currentThread().getName() + " ocupa un lugar en el puesto");
    }

    // Pasajero sale del puesto
    public void salirPuesto() {
        System.out.println(Thread.currentThread().getName() + " sale del puesto de " + nombre);
        capacidad.release();
    }

    // Metodo compatible con Guardia.java: el control de capacidad ya lo maneja
    // el Semaphore, asi que este metodo es un no-op. Se mantiene para que
    // Guardia pueda crear threads sin errores de compilacion.
    public void permitirIngreso() throws InterruptedException {
        // No-op: el Semaphore de capacidad controla el ingreso
        Thread.sleep(100); // evitar busy-loop
    }

    // Intercambio de boleto (usado por pasajero)
    public String[] realizarIntercambio(String[] boletoAvion) throws InterruptedException {
        synchronized (this) {
            while (intercambioEnCurso) {
                wait();
            }
            intercambioEnCurso = true;
        }
        try {
            // El exchanger es thread-safe
            return exchanger.exchange(boletoAvion);
        } finally {
            synchronized (this) {
                intercambioEnCurso = false;
                notifyAll();
            }
        }
    }

    // Intercambio desde el empleado
    public void intercambio() throws InterruptedException {
        synchronized (this) {
            while (!intercambioEnCurso) {
                wait();
            }
            System.out.println(Thread.currentThread().getName() + " entrega un boleto de terminal");
            crearBoletoTerminal();
        }
        try {
            exchanger.exchange(boletoTerminal);
        } finally {
            // No es necesario resetear intercambioEnCurso aquí porque lo hace el pasajero
        }
    }

    private void crearBoletoTerminal() {
        String[] boleto = new String[2];
        int numeroTerminal = (int) (Math.random() * cantidadTerminal);
        boleto[0] = Character.toString((char) ('A' + numeroTerminal));
        int puertoTerminal = (int) (Math.random()
                * (tamanioPE[numeroTerminal][1] - tamanioPE[numeroTerminal][0] + 1))
                + tamanioPE[numeroTerminal][0];
        boleto[1] = Integer.toString(puertoTerminal);
        this.boletoTerminal = boleto;
    }
}