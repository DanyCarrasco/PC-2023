package tpObligatorio;

public class EmpleadoPuesto implements Runnable {
    private PuestoAtencion puesto;

    public EmpleadoPuesto(PuestoAtencion puesto) {
        this.puesto = puesto;
    }

    public void run() {
        int i = 0;
        try {
            while (i < 5) {
                puesto.intercambio();
                i++;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
