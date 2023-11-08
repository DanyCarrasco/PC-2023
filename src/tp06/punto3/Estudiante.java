package tp06.punto3;

public class Estudiante implements Runnable{
    private ControlSalaEstudio sala;
    public Estudiante(ControlSalaEstudio sala){
        this.sala = sala;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+" quiere entrar a la sala de estudio");
        sala.ingresar();
        System.out.println(Thread.currentThread().getName()+ " entra a la sala y ocupa una mesa");
        estudiar();
        System.out.println(Thread.currentThread().getName() +" sale de la sala de estudio");
        sala.salir();
    }

    private void estudiar(){
        System.out.println(Thread.currentThread().getName()+" empieza a estudiar");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName()+ " termino de estudiar");
    }
}
