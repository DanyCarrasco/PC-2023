package tpObligatorio;

public class IngresoAeropuerto {
    public PuestoAtencion[] puestos;
    private String[] nombresLineas;
    private int cantMaxima;
    private int cantTerminales;
    private int[][] tamanioPE; //PE: Puestos de Embarque

    public PuestoInformes informe;

    public IngresoAeropuerto(String[] nombresLineas, int cantidadMaxima, int cantidadTerminales,
            int[][] tamanioPE) {
        this.puestos = new PuestoAtencion[nombresLineas.length];
        this.nombresLineas = nombresLineas;
        this.cantMaxima = cantidadMaxima;
        this.cantTerminales = cantidadTerminales;
        this.tamanioPE = tamanioPE;
        iniciarPuestosAtencion();
        informe = new PuestoInformes(puestos);
    }

    private void iniciarPuestosAtencion(){
        for (int i = 0; i < nombresLineas.length; i++) {
            puestos[i] = new PuestoAtencion(this.nombresLineas[i], cantMaxima, cantTerminales, tamanioPE);
        }
    }
}
