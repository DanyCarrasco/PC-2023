package tpObligatorio;

public class Guardia implements Runnable {
    private PuestoAtencion puesto;

    public Guardia(PuestoAtencion puesto) {
        this.puesto = puesto;
    }

    public void run() {
        try {
            while (true) {
                puesto.permitirIngreso();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " guardia interrumpido");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " error en guardia: " + e.getMessage());
        }
    }

}
