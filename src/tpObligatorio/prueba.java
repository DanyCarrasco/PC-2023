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
        PuestoAtencion[] puestos = { puesto, puesto2 };
        PuestoInformes informes = new PuestoInformes(puestos);

        Thread guardia = new Thread(new Guardia(puesto), "Guardia");
        Thread empleado = new Thread(new EmpleadoPuesto(puesto), "Empleado");
        guardia.start();
        empleado.start();
        Thread guardia2 = new Thread(new Guardia(puesto2), "Guardia 2");
        Thread empleado2 = new Thread(new EmpleadoPuesto(puesto2), "Empleado 2");
        guardia2.start();
        empleado2.start();

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
            PuestoAtencion puestoAsignado = informes.derivarAPuesto();
            int term = (int) (Math.random() * cantidadTerminales);
            pasajeros[i] = new Thread(
                    new Pasajero(puestoAsignado, transporte, tiendas[term], sala, i % 2 == 0),
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
