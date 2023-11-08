package tp07.punto2;

public class Programador implements Runnable{
    private Recurso recursos;
    public Programador(Recurso r){
        recursos = r;
    }

    public void run() {
        recursos.acceso();
        trabajar();
        recursos.salir();
    }

    private void trabajar(){
        System.out.println(Thread.currentThread().getName()+" empieza a trabajar");
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
        }
        System.out.println(Thread.currentThread().getName()+" termino de trabajar");
    }
}
