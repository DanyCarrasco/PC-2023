package tp08.punto2.monitores;

public class Mantenimiento implements Runnable{
    private SalaMonitores sala;
    public Mantenimiento(SalaMonitores sala){
        this.sala = sala;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+ " quiere entrar al observatorio");
        sala.ingresaMantenimiento();
        System.out.println(Thread.currentThread().getName()+" entra al observatorio");
        controlar();
        System.out.println(Thread.currentThread().getName()+ " sale del observatorio");
        sala.saleMantenimiento();
    }

    private void controlar(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
