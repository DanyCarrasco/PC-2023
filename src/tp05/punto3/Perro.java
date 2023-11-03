package tp05.punto3;
public class Perro implements Runnable{
    private Comedor2 platos;
    public Perro(Comedor2 platos){
        this.platos = platos;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+ " quiere entrar al comedor");
        platos.entraPerro();      
        comer();
        System.out.println(Thread.currentThread().getName()+" quiere salir");
        platos.salePerro();
    }

    private void comer(){
        System.out.println(Thread.currentThread().getName()+" empieza a comer");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" termino a comer");
    }
}
