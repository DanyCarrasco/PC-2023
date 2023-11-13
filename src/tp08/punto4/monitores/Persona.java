package tp08.punto4.monitores;

public class Persona implements Runnable{
    private HemoterapiaMonitores centro;
    public Persona(HemoterapiaMonitores centro){
        this.centro = centro;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+ " quiere ingresar a la sala de extraccion");
        centro.ingresarExtraccion();
        System.out.println(Thread.currentThread().getName()+ " ingresa a la sala de extraccion");
        System.out.println(Thread.currentThread().getName()+ " comienza la extraccion de sangre");
        try{
            Thread.sleep(500);
        }catch(InterruptedException e){
        }
        System.out.println(Thread.currentThread().getName()+ " termina la extraccion de sangre");
        System.out.println(Thread.currentThread().getName()+ " sale de la sala de extraccion");
        centro.dejarExtraccion();
    }
}
