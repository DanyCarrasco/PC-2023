package tp08.punto5.semaforos;

public class Canibal implements Runnable{
    private OllaSemaforos ollaTribu;
    public Canibal(OllaSemaforos ollaTribu){
        this.ollaTribu = ollaTribu;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+" quiere comer de la olla");
        ollaTribu.puedeComer();
        System.out.println(Thread.currentThread().getName()+" obtiene una racion de comida");
        ollaTribu.comer();
        comer();
        System.out.println(Thread.currentThread().getName()+ " termino");
    }

    private void comer(){
        System.out.println(Thread.currentThread().getName()+" empieza a comer");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName()+" termino de comer");
    }
}
