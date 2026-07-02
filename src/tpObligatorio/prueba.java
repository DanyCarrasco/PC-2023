package tpObligatorio;

public class prueba {
    public static void main(String[] args) {
        int cantidadPasajeros = 10;

        //Definicion que se implementa en clase Aeropuerto

        Aeropuerto aeropuerto = new Aeropuerto(cantidadPasajeros);

        ControlAeropuerto control = new ControlAeropuerto(aeropuerto);

        Thread administrador = new Thread(new AdministradorAeropuerto(control), "Administrador del Aeropuerto");
        administrador.start();

        //Se implementan en la clase 'prueba' los empleados
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

        Thread[] pasajeros = new Thread[10];
        for (int i = 0; i < cantidadPasajeros; i++) {
            pasajeros[i] = new Thread(
                    new Pasajero(control, aeropuerto, i % 2 == 0),
                    "Pasajero #" + i);
            pasajeros[i].start();
        }

        for (Thread t : pasajeros) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Todos los pasajeros han finalizado.");
    }

}
