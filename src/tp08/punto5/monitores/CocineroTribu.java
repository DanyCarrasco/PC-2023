package tp08.punto5.monitores;

public class CocineroTribu implements Runnable{
    private OllaMonitores olla;
    public CocineroTribu(OllaMonitores olla){
        this.olla = olla;
    }

    public void run(){
        while(true){
            olla.empiezaACocinar();
            cocinar();
            olla.terminoDeCocinar();
        }
    }

    private void cocinar(){
        System.out.println("Cocinero empieza a cocinar");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Cocinero termino de cocinar");
    }
}
