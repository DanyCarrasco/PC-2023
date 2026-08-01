package tpObligatorio;

public class IngresoAeropuerto {
    public PuestoAtencion[] puestos;
    private String[] nombresLineas;
    private int cantMaxima;
    private int[][] tamanioPE; // PE: Puestos de Embarque

    public PuestoInformes informe;

    public IngresoAeropuerto(int cantidadMaxima, Terminal[] terminales) {
        this.cantMaxima = cantidadMaxima;
        definirPuertosEmbarque();
        iniciarNombresLineas();
        iniciarPuestosAtencion(terminales);
        informe = new PuestoInformes(puestos);
    }

    private void definirPuertosEmbarque() {
        this.tamanioPE = new int[3][2];
        tamanioPE[0][0] = 1;
        tamanioPE[1][0] = 8;
        tamanioPE[2][0] = 16;
        tamanioPE[0][1] = 7;
        tamanioPE[1][1] = 15;
        tamanioPE[2][1] = 20;
    }

    private void iniciarNombresLineas() {
        nombresLineas = new String[3];
        nombresLineas[0] = "Aerolineas Argentinas";
        nombresLineas[1] = "LATAM";
        nombresLineas[2] = "JetSMART";
    }

    private void iniciarPuestosAtencion(Terminal[] terminales) {
        this.puestos = new PuestoAtencion[nombresLineas.length];
        for (int i = 0; i < nombresLineas.length; i++) {
            puestos[i] = new PuestoAtencion(this.nombresLineas[i], cantMaxima, 3, tamanioPE, terminales);
        }
    }
}
