package tpObligatorio;

import java.util.concurrent.Exchanger;

public class PuestoAtencion {
    private String nombre;
    private int cantidadTerminal;
    private int[][] tamanioPE; // PE: Puertos de Embarques

    // Capacidad máxima de pasajeros simultáneos (lo único que justifica un contador)
    private int maxPasajeros;
    private int pasajerosActuales = 0;

    // Estado del monitor
    private boolean guardiaDisponible = true;
    private boolean pasajeroEsperando = false;
    private boolean intercambioEnCurso = false;

    private final Exchanger<String[]> exchanger = new Exchanger<>();
    private String[] boletoTerminal;

    public PuestoAtencion(String nombre, int cantidadMaxima, int cantidadTerminales, int[][] tamanioPE) {
        this.nombre = nombre;
        this.cantidadTerminal = cantidadTerminales;
        this.tamanioPE = tamanioPE;
        this.maxPasajeros = cantidadMaxima;
        this.boletoTerminal = new String[2];
    }

    // ----------- PASAJERO: pedir ingreso -----------
    public synchronized void puedeEntrarPuesto() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()
                + " intenta ingresar al puesto de atencion de " + nombre);

        // Espera a que el guardia esté libre
        while (!guardiaDisponible) {
            wait();
        }
        guardiaDisponible = false;

        // Queda registrado que hay un pasajero pidiendo entrar
        pasajeroEsperando = true;
        notifyAll(); // despierta al guardia
    }

    // ----------- GUARDIA: dar permiso -----------
    public synchronized void permitirIngreso() throws InterruptedException {
        // Espera hasta que haya un pasajero pidiendo entrar
        while (!pasajeroEsperando) {
            wait();
        }
        pasajeroEsperando = false;

        // Limita la cantidad de pasajeros simultáneos en el puesto
        while (pasajerosActuales >= maxPasajeros) {
            wait();
        }
        pasajerosActuales++;

        guardiaDisponible = true;
        System.out.println(Thread.currentThread().getName() + " ingresa a un pasajero");
        notifyAll(); // despierta al pasajero que esperaba entrar
    }

    // ----------- PASAJERO: salir del puesto -----------
    public synchronized void salirPuesto() {
        System.out.println(Thread.currentThread().getName()
                + " sale del puesto de atencion de pasajeros de " + nombre);
        pasajerosActuales--;
        notifyAll();
    }

    // ----------- PASAJERO: pedir intercambio de boleto -----------
    public String[] realizarIntercambio(String[] boletoAvion) throws InterruptedException {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName()
                    + " espera su turno para el boleto");
            // Espera a que no haya otro intercambio en curso
            while (intercambioEnCurso) {
                wait();
            }
            intercambioEnCurso = true;
        }
        // Sale del monitor para no bloquear a otros hilos durante el exchange

        // El exchanger es thread-safe, así que se hace fuera del lock
        return exchanger.exchange(boletoAvion);
    }

    // ----------- TRABAJADOR: entregar boleto -----------
    public void intercambio() throws InterruptedException {
        synchronized (this) {
            // Espera hasta que un pasajero haya pedido intercambio
            while (!intercambioEnCurso) {
                wait();
            }
            System.out.println(Thread.currentThread().getName()
                    + " entrega un boleto de terminal a un pasajero");
            crearBoletoTerminal();
        }
        // Exchange fuera del lock
        exchanger.exchange(boletoTerminal);

        synchronized (this) {
            intercambioEnCurso = false;
            notifyAll();
        }
    }

    // ----------- Generación de boleto (no synchronized, solo lee estado) -----------
    private void crearBoletoTerminal() {
        String[] boleto = new String[2];
        int numeroTerminal = (int) (Math.random() * cantidadTerminal);
        boleto[0] = Character.toString('A' + numeroTerminal);
        int puertoTerminal = (int) (Math.random()
                * (tamanioPE[numeroTerminal][1] - tamanioPE[numeroTerminal][0] + 1))
                + tamanioPE[numeroTerminal][0];
        boleto[1] = Integer.toString(puertoTerminal);
        this.boletoTerminal = boleto;
    }
}