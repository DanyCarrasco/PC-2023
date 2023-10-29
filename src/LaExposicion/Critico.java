package LaExposicion;

public class Critico implements Runnable{
    private Sala exposicion;

    public Critico(Sala exposicion){
        this.exposicion = exposicion;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+" quiere entrar a la exposicion");
        exposicion.entraCritico();
        analizarYCriticar();
        System.out.println(Thread.currentThread().getName()+ " sale de la exposicion");
        exposicion.saleCritico();

    }

    private void analizarYCriticar(){
        System.out.println(Thread.currentThread().getName()+ " empieza a analizar los cuadros");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+ " termina de analizar y deja su critica en la coleccion de criticas");
    }
}
