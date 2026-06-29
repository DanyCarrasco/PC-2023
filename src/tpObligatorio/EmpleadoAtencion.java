package tpObligatorio;

public class EmpleadoAtencion implements Runnable {
    private PuestoAtencion puesto;

    public EmpleadoAtencion(PuestoAtencion puesto) {
        this.puesto = puesto;
    }

    public void run() {
        try {
            while (true) {
                puesto.intercambio();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " empleado interrumpido");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " error en empleado: " + e.getMessage());
        }
    }

}
