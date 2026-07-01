package tpObligatorio;

public class Aeropuerto {
    private Terminal[] terminales;
    private TransporteATerminal transporte;

    public Aeropuerto(int cantTerminales) {
        terminales = new Terminal[cantTerminales];
        transporte = new TransporteATerminal(3, cantTerminales, 10); // despues modificarlo, porque no se sabe la
                                                                     // cantidad de pasajeros
        iniciarTerminales();
    }

    public void asignarCantidadPasajeros(){
        int cantidad;
        for (int i = 0; i < terminales.length; i++) {
            cantidad = 0;
            while(cantidad == 0){
                cantidad = transporte.getCantidadPasajerosTerminal(i);
            }
            terminales[i].cambiarCantidadPasajeros(cantidad);
        }
    }

    private void iniciarTerminales() {
        if (terminales.length > 0 && terminales.length < 27) {
            for (int i = 0; i < terminales.length; i++) {
                terminales[i] = new Terminal(Character.toString('A' + i), 3);
            }
        } else {
            System.out.println("Ingreso una cantidad que supera la cantidad de numeros en el abecedario o es menor a 1");
        }
    }

}
