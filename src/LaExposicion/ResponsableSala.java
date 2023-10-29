package LaExposicion;

public class ResponsableSala implements Runnable{
    private Sala exposicion;

    public ResponsableSala(Sala exposicion){
        this.exposicion = exposicion;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+ " quiere entrar a la exposicion");
        exposicion.entraResponsable();
        System.out.println(Thread.currentThread().getName()+" entra a la exposicion");
        controlar();
        System.out.println(Thread.currentThread().getName()+ " sale de la sala de exposicion");
        exposicion.saleResponsable();
    }

    private void controlar(){
        System.out.println();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println();
    }
}
