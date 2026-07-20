package tpObligatorio;

public class Main {
    public static void main(String[] args) {
        // Se ejecuto el programa con 20 pasajeros, 3 terminales, 2 puestos de ingreso y 2 cajeros por terminal.
        // Se puede cambiar la cantidad de pasajeros para probar el programa con diferentes escenarios

        int cantidadPasajeros = 20;

        Aeropuerto aeropuerto = new Aeropuerto(cantidadPasajeros);
        ControlAeropuerto control = new ControlAeropuerto(aeropuerto);

        Thread administrador = new Thread(new AdministradorAeropuerto(control), "Administrador del Aeropuerto");
        administrador.start();

        Thread guardia = new Thread(new Guardia(aeropuerto.entrada.puestos[0]), "Guardia");
        Thread empleadoAtencion = new Thread(new EmpleadoAtencion(aeropuerto.entrada.puestos[0]), "Empleado");
        guardia.start();
        empleadoAtencion.start();

        Thread guardia2 = new Thread(new Guardia(aeropuerto.entrada.puestos[1]), "Guardia 2");
        Thread empleadoAtencion2 = new Thread(new EmpleadoAtencion(aeropuerto.entrada.puestos[1]), "Empleado 2");
        guardia2.start();
        empleadoAtencion2.start();

        Thread guardia3 = new Thread(new Guardia(aeropuerto.entrada.puestos[2]), "Guardia 3");
        Thread empleadoAtencion3 = new Thread(new EmpleadoAtencion(aeropuerto.entrada.puestos[2]), "Empleado 3");
        guardia3.start();
        empleadoAtencion3.start();

        Thread empleadoInforme = new Thread(new EmpleadoInforme(aeropuerto.entrada.informe), "Empleado Informe");
        empleadoInforme.start();
        
        Thread[] empleadosSalon= new Thread[3];
        Thread[][] cajeros = new Thread[3][2];
        for (int t = 0; t < cajeros.length; t++) {
            empleadosSalon[t] = new Thread(new EmpleadoSalon(aeropuerto.terminales[t]), "Empleado Salon "+ aeropuerto.terminales[t].getId());
            empleadosSalon[t].start();
            for (int c = 0; c < 2; c++) {
                cajeros[t][c] = new Thread(new Cajero(aeropuerto.terminales[t].tienda), "Cajero " + aeropuerto.terminales[t].getId() + "-" + (c + 1));
                cajeros[t][c].start();
            }
        }

        Thread[] pasajeros = new Thread[cantidadPasajeros];
        for (int i = 0; i < cantidadPasajeros; i++) {
            pasajeros[i] = new Thread(
                    new Pasajero(control, aeropuerto, i % 2 == 0),
                    "Pasajero #" + i);
            pasajeros[i].start();
        }

        Thread vuelo = new Thread(() -> {
            try {
                aeropuerto.avionDespega.await();
                System.out.println("=== EL AVION DESPEGA CON TODOS LOS PASAJEROS A BORDO ===");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Vuelo");
        vuelo.start();
    }

}
