package tpObligatorio;

public class EmpleadoInforme implements Runnable {
    private PuestoInformes puesto;

    public EmpleadoInforme(PuestoInformes puesto) {
        this.puesto = puesto;
    }

    public void run() {
        while (true) {
            puesto.atenderPasajero();
        }
    }
}
