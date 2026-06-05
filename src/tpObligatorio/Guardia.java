package tpObligatorio;

public class Guardia implements Runnable{
    private PuestoAtencion puesto;

    public Guardia(PuestoAtencion puesto){
        this.puesto = puesto;
    }

    public void run() {
        int i = 0;
        try {
            while (i < 5) {
                puesto.permitirIngreso();
                Thread.sleep(5000);
                i++;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
}
