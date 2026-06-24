package tpObligatorio;

public class IngresoAeropuerto {
    private PuestoAtencion[] puestos;
    private String[] nombresLineas;
    private int cantMaxima;
    private int cantTerminales;
    private int[][] tamanioPE; //PE: Puestos de Embarque

    public IngresoAeropuerto(int cantPuestos, String[] nombresLineas, int cantidadMaxima, int cantidadTerminales,
            int[][] tamanioPE) {
        this.puestos = new PuestoAtencion[cantPuestos];
        this.nombresLineas = nombresLineas;
        this.cantMaxima = cantidadMaxima;
        this.cantTerminales = cantidadTerminales;
        this.tamanioPE = tamanioPE;
        iniciarPuestosAtencion();
    }

    private void iniciarPuestosAtencion(){
        for (int i = 0; i < puestos.length; i++) {
            puestos[i] = new PuestoAtencion(this.nombresLineas[i], cantMaxima, cantTerminales, tamanioPE);
        }
    }
}
