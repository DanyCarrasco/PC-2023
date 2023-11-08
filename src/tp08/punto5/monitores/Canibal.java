package tp08.punto5.monitores;

public class Canibal implements Runnable{
    private OllaMonitores olla;
    public Canibal(OllaMonitores olla){
        this.olla = olla;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+" quiere una racion de la olla");
        olla.empiezaAComer();
        comerRacion();
    }

    private void comerRacion(){
        System.out.println(Thread.currentThread().getName()+" comienza a comer racion");
        try {
            Thread.sleep(500);
        }catch(InterruptedException e){}
        System.out.println(Thread.currentThread().getName()+" termino de comer racion");
    }
}
