package tp08.punto5.locks;

public class Canibal implements Runnable{
    private OllaLocks olla;
    public Canibal(OllaLocks olla){
        this.olla = olla;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+" quiere comer una racion de comida");
        olla.comer();
    }
}
