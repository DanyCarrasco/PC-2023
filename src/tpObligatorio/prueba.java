package tpObligatorio;

public class prueba {
    public static void main(String[] args) {
        int cantPasajeros = 10, cantidadTerminales = 3;
        int[][] terminales = new int[cantidadTerminales][2];
        terminales[0][0] = 1;
        terminales[1][0] = 8;
        terminales[2][0] = 16;

        terminales[0][1] = 7;
        terminales[1][1] = 15;
        terminales[2][1] = 20;

        PuestoAtencion puesto = new PuestoAtencion("Aerolineas Argentinas", 2, cantidadTerminales, terminales);
        PuestoAtencion puesto2 = new PuestoAtencion("LATAM", 2, cantidadTerminales, terminales);
        Thread guardia = new Thread((new Guardia(puesto)), "Guardia");
        Thread empleado = new Thread((new EmpleadoPuesto(puesto)), "Empleado");
        guardia.start();
        empleado.start();
        Thread guardia2 = new Thread((new Guardia(puesto2)), "Guardia 2");
        Thread empleado2 = new Thread((new EmpleadoPuesto(puesto2)), "Empleado 2");
        guardia2.start();
        empleado2.start();

        TransporteATerminal transporte = new TransporteATerminal(5, cantidadTerminales);

        FreeShop tienda = new FreeShop("A", 3);
        // Thread cajero = new Thread((new Cajero (tienda)), "Cajero");
        // cajero.start();

        Thread[] pasajeros = new Thread[cantPasajeros];
        for (int i = 0; i < cantPasajeros; i++) {
            if (i % 2 == 0) {
                pasajeros[i] = new Thread(new Pasajero(puesto2, transporte, tienda, true), "Pasajero #" + i);
            } else {
                pasajeros[i] = new Thread(new Pasajero(puesto, transporte, tienda, false), "Pasajero #" + i);
            }
            pasajeros[i].start();
        }

        // Esperar a que todos terminen
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
