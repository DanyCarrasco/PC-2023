package LaExposicion;

public class Visitantes implements Runnable {
    private Sala exposicion;

    public Visitantes(Sala exposicion) {
        this.exposicion = exposicion;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName()+" quiere entrar a a la exposicion");
        exposicion.entraVisitante();
        System.out.println(Thread.currentThread().getName() + " entra a la exposicion");
        disfrutarCuadros();
        System.out.println(Thread.currentThread().getName() + " sale de la sala de exposicion");
        exposicion.saleVisitante();
    }

    private void disfrutarCuadros() {
        System.out.println(Thread.currentThread().getName() + " disfruta de la exposicion");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " termino de disfrutar de la exposicion");
    }
}
