package tpObligatorio;

public class prueba {
    public static void main(String[] args) {

        //Definicion que se implementa en clase Aeropuerto
        int cantPasajeros = 10, cantidadTerminales = 3;
        int[][] terminales = new int[cantidadTerminales][2];
        terminales[0][0] = 1;
        terminales[1][0] = 8;
        terminales[2][0] = 16;
        terminales[0][1] = 7;
        terminales[1][1] = 15;
        terminales[2][1] = 20;

        String [] aerolineas = new String[3];
        aerolineas[0] = "Aerolineas Argentinas";
        aerolineas[1] = "LATAM";
        aerolineas[2] = "JetSMART";

        IngresoAeropuerto entrada = new IngresoAeropuerto(aerolineas, cantPasajeros, cantidadTerminales, terminales);

        //Se implementan en la clase 'prueba'
        Thread guardia = new Thread(new Guardia(entrada.puestos[0]), "Guardia");
        Thread empleadoAtencion = new Thread(new EmpleadoAtencion(entrada.puestos[0]), "Empleado");
        guardia.start();
        empleadoAtencion.start();

        Thread guardia2 = new Thread(new Guardia(entrada.puestos[1]), "Guardia 2");
        Thread empleadoAtencion2 = new Thread(new EmpleadoAtencion(entrada.puestos[1]), "Empleado 2");
        guardia2.start();
        empleadoAtencion2.start();

        Thread guardia3 = new Thread(new Guardia(entrada.puestos[2]), "Guardia");
        Thread empleadoAtencion3 = new Thread(new EmpleadoAtencion(entrada.puestos[2]), "Empleado");
        guardia3.start();
        empleadoAtencion3.start();

        Thread empleadoInforme = new Thread(new EmpleadoInforme(entrada.informe), "Empleado Informe");
        empleadoInforme.start();


        // definicion de Terminal de cada uno y de Aeropuerto
        TransporteATerminal transporte = new TransporteATerminal(5, cantidadTerminales, cantPasajeros);

        FreeShop[] tiendas = new FreeShop[cantidadTerminales];
        Thread[][] cajeros = new Thread[cantidadTerminales][2];
        String[] idTerminal = { "A", "B", "C" };
        for (int t = 0; t < cantidadTerminales; t++) {
            tiendas[t] = new FreeShop(idTerminal[t], 3);
            for (int c = 0; c < 2; c++) {
                cajeros[t][c] = new Thread(new Cajero(tiendas[t]), "Cajero " + idTerminal[t] + "-" + (c + 1));
                cajeros[t][c].start();
            }
        }

        SalaEmbarque sala = new SalaEmbarque(cantPasajeros);

        Thread[] pasajeros = new Thread[cantPasajeros];
        for (int i = 0; i < cantPasajeros; i++) {
            int term = (int) (Math.random() * cantidadTerminales);
            pasajeros[i] = new Thread(
                    new Pasajero(entrada.informe, transporte, tiendas[term], sala, i % 2 == 0),
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

        sala.llamarAEmbarcar();

        System.out.println("Todos los pasajeros han finalizado.");
    }

}
