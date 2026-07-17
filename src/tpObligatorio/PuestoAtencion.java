package tpObligatorio;

import java.util.concurrent.Exchanger;

public class PuestoAtencion {
    private String nombre;
    private int cantidadTerminal;
    private int[][] tamanioPE; // PE: Puertos de Embarques
    private int permisosPendientes;

    // Capacidad máxima de pasajeros simultáneos (lo único que justifica un
    // contador)
    private int maxPasajeros;

    private int activos; // pasajaros actualmente dentro del puesto
    private int esperando; // pasajeros esperando permiso del guardia

    private boolean intercambioEnCurso = false;
    private final Exchanger<String[]> exchanger = new Exchanger<>();
    private String[] boletoTerminal;

    public PuestoAtencion(String nombre, int cantidadMaxima, int cantidadTerminales, int[][] tamanioPE) {
        this.nombre = nombre;
        this.cantidadTerminal = cantidadTerminales;
        this.tamanioPE = tamanioPE;
        this.maxPasajeros = cantidadMaxima;
        this.activos = 0;
        this.esperando = 0;
        this.boletoTerminal = new String[2];
        this.permisosPendientes = 0;
    }

    public String getNombre() {
        return nombre;
    }

    // ----------- PASAJERO: pedir ingreso -----------
    // Llamado por el pasajero para solicitar ingreso (bloquea hasta que el guardia
    // le dé el permiso)
    public synchronized void puedeEntrarPuesto() throws InterruptedException {
        esperando++;
        System.out.println(Thread.currentThread().getName()
                + " espera para ingresar al puesto de atencion de " + nombre);

        // Avisa al guardia que ahora hay (al menos) un pasajero esperando,
        // ya que el guardia puede estar bloqueado en wait() esperando esto.
        notifyAll();

        try {
            while (permisosPendientes == 0) {
                wait();
            }
            // Consume un permiso pendiente
            permisosPendientes--;
            // Control de capacidad: el guardia ya verifico que activos < maxPasajeros
            activos++;
        } finally {
            esperando--;
        }
        notifyAll(); // avisa al guardia que el permiso fue consumido
        System.out.println(Thread.currentThread().getName() + " ingresa al puesto de atencion de " + nombre);
    }

    // ----------- GUARDIA: dar permiso -----------
    // Llamado por el guardia para permitir la entrada de un pasajero (si hay
    // esperando y cupo)
    public synchronized void permitirIngreso() throws InterruptedException {
        while (esperando == 0 || activos >= maxPasajeros || permisosPendientes > 0) {
            wait();
        }
        permisosPendientes++;
        notifyAll(); // despierta a un pasajero que estaba esperando permiso
        System.out.println(Thread.currentThread().getName() + " da permiso a un pasajero");
    }

    // ----------- PASAJERO: salir del puesto -----------
    // Llamado por el pasajero al salir del puesto
    public synchronized void salirPuesto() {
        activos--;
        System.out.println(Thread.currentThread().getName()
                + " sale del puesto de atencion de pasajeros de " + nombre);
        notifyAll(); // despierta al guardia por si estaba esperando cupo
    }

    // ----------- PASAJERO: pedir intercambio de boleto -----------
    // Intercambio de boleto (usado por pasajero)
    public String[] realizarIntercambio(String[] boletoAvion) throws InterruptedException {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName()
                    + " espera su turno para el boleto");
            // Espera a que no haya otro intercambio en curso
            while (intercambioEnCurso) {
                wait();
            }
            System.out.println(Thread.currentThread().getName() + " solicita intercambio");
            intercambioEnCurso = true;
            notifyAll();
        }
        try {
            System.out.println(Thread.currentThread().getName() + " esperando exchange");
            return exchanger.exchange(boletoAvion);
        } finally {
            synchronized (this) {
                intercambioEnCurso = false;
                notifyAll();
            }
        }
    }

    // ----------- TRABAJADOR: entregar boleto -----------
    // Intercambio desde el empleado del puesto
    public void intercambio() throws InterruptedException {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " esperando intercambio");
            // Espera hasta que un pasajero haya pedido intercambio
            while (!intercambioEnCurso) {
                wait();
            }
            System.out.println(Thread.currentThread().getName() + " despertó");
            System.out.println(Thread.currentThread().getName()
                    + " entrega un boleto de terminal a un pasajero");
            crearBoletoTerminal();
        }
        try {
            // Exchange fuera del lock
            exchanger.exchange(boletoTerminal);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw e;
        }
    }

    // ----------- Generación de boleto (no synchronized, solo lee estado)
    // -----------
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