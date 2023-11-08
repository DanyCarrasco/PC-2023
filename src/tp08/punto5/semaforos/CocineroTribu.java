package tp08.punto5.semaforos;

public class CocineroTribu implements Runnable {
    private final OllaSemaforos ollaSemaforosTribu;

    public CocineroTribu(OllaSemaforos ollaSemaforos) {
        ollaSemaforosTribu = ollaSemaforos;
    }

    public void run() {
        while (true) {
            ollaSemaforosTribu.esperaPedidoCocinero();
            cocinar();
            ollaSemaforosTribu.avisoCanibal();
        }
    }

    private void cocinar(){
        System.out.println("Cocinero de la tribu cocina en la olla");
        try{
            Thread.sleep(500);
        }catch(InterruptedException e){}
        System.out.println("Cocinero de la tribu termino de cocinar en la olla");
    }
}
