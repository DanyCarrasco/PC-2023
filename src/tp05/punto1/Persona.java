package tp05.punto1;

public class Persona implements Runnable{
    private GestorPiscina piscina;

    public Persona(GestorPiscina piscina){
        this.piscina = piscina;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+" quiere ingresar a la piscina");
        piscina.ingresar();
        System.out.println(Thread.currentThread().getName()+" ingresa a la piscina");
        disfrutarPiscina();
        System.out.println(Thread.currentThread().getName()+ " sale de la piscina");
        piscina.salir();
    }

    private void disfrutarPiscina(){
        //simula el tiempo de disfrutar en la piscina
        System.out.println(Thread.currentThread().getName()+ " disfruta de la piscina");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+ " quiere salir de la piscina");
    }
}
