package tpObligatorio;

public class Aeropuerto {
    public Terminal[] terminales;
    public TransporteATerminal transporte;
    public IngresoAeropuerto entrada;

    public Aeropuerto(int cantidadPasajeros) {
        terminales = new Terminal[3];
        transporte = new TransporteATerminal(5, 3, cantidadPasajeros); // despues modificarlo, porque no se sabe la
                                                                     // cantidad de pasajeros
        String [] aerolineas = new String[3];
        aerolineas[0] = "Aerolineas Argentinas";
        aerolineas[1] = "LATAM";
        aerolineas[2] = "JetSMART";

        int[][] puertosEmbarque = new int[3][2];

        for (int i = 0; i < puertosEmbarque.length; i++) {
            puertosEmbarque[i][0] = terminales[i].getPuertoInicio();
            puertosEmbarque[i][1] = terminales[i].getPuertoFinal();
        }


        entrada = new IngresoAeropuerto(aerolineas, 2, 3, puertosEmbarque);
        iniciarTerminales();
    }

    public void asignarCantidadPasajeros() {
        int cantidad;
        for (int i = 0; i < terminales.length; i++) {
            cantidad = 0;
            while (cantidad == 0) {
                cantidad = transporte.getCantidadPasajerosTerminal(i);
            }
            terminales[i].cambiarCantidadPasajeros(cantidad);
        }
    }

    private void iniciarTerminales() {
        terminales[0] = new Terminal(Character.toString('A'), 3, 1, 7);
        terminales[1] = new Terminal(Character.toString('B'), 3, 8, 15);
        terminales[2] = new Terminal(Character.toString('C'), 3, 16, 20);
    }

}
