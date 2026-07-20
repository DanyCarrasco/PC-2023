package tpObligatorio;

import java.util.concurrent.CountDownLatch;

public class Aeropuerto {
    public Terminal[] terminales;
    public TransporteATerminal transporte;
    public IngresoAeropuerto entrada;
    public CountDownLatch avionDespega;

    public Aeropuerto(int cantidadPasajeros) {
        terminales = new Terminal[3];
        transporte = new TransporteATerminal(5, 3, cantidadPasajeros); 
        entrada = new IngresoAeropuerto(2);
        avionDespega = new CountDownLatch(cantidadPasajeros);
        iniciarTerminales();
    }

    private void iniciarTerminales() {
        terminales[0] = new Terminal(Character.toString('A'), 3);
        terminales[1] = new Terminal(Character.toString('B'), 3);
        terminales[2] = new Terminal(Character.toString('C'), 3);
    }

}
