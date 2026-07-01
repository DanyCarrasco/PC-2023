package tpObligatorio;

public class prueba {
    public static void main(String[] args) {
        int cantidadPasajeros = 10;

        //Definicion que se implementa en clase Aeropuerto

        Aeropuerto aeropuerto = new Aeropuerto(cantidadPasajeros);
        

        String [] aerolineas = new String[3];
        aerolineas[0] = "Aerolineas Argentinas";
        aerolineas[1] = "LATAM";
        aerolineas[2] = "JetSMART";

        int[][] puertosEmbarque = new int[3][2];

        for (int i = 0; i < puertosEmbarque.length; i++) {
            puertosEmbarque[i][0] = aeropuerto.terminales[i].getPuertoInicio();
            puertosEmbarque[i][1] = aeropuerto.terminales[i].getPuertoFinal();
        }


        IngresoAeropuerto entrada = new IngresoAeropuerto(aerolineas, 2, 3, puertosEmbarque);

        //Se implementan en la clase 'prueba'
        Thread guardia = new Thread(new Guardia(entrada.puestos[0]), "Guardia");
        Thread empleadoAtencion = new Thread(new EmpleadoAtencion(entrada.puestos[0]), "Empleado");
        guardia.start();
        empleadoAtencion.start();

        Thread guardia2 = new Thread(new Guardia(entrada.puestos[1]), "Guardia 2");
        Thread empleadoAtencion2 = new Thread(new EmpleadoAtencion(entrada.puestos[1]), "Empleado 2");
        guardia2.start();
        empleadoAtencion2.start();

        Thread guardia3 = new Thread(new Guardia(entrada.puestos[2]), "Guardia 3");
        Thread empleadoAtencion3 = new Thread(new EmpleadoAtencion(entrada.puestos[2]), "Empleado 3");
        guardia3.start();
        empleadoAtencion3.start();

        Thread empleadoInforme = new Thread(new EmpleadoInforme(entrada.informe), "Empleado Informe");
        empleadoInforme.start();


        // definicion de Terminal de cada uno y de Aeropuerto
        
        
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
                    new Pasajero(entrada, aeropuerto, i % 2 == 0),
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
