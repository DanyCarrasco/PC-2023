package tp08.punto5.locks;

public class CocineroTribu implements Runnable{
    private OllaLocks olla;
    public CocineroTribu(OllaLocks olla){
        this.olla = olla;
    }

    public void run(){
        while (true){
            olla.cocinar();
        }
    }
}
