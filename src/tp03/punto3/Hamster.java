package tp03.punto3;

public class Hamster implements Runnable {
    private Jaula jaula;

    public Hamster() {
        jaula = new Jaula();
    }

    @Override
    public void run() {
        jaula.tomaPlato(Thread.currentThread().getName());
        tiempo();
        jaula.dejaPlato(Thread.currentThread().getName());
        /*jaula.tomaRueda(Thread.currentThread().getName());
        tiempo();
        jaula.dejaRueda(Thread.currentThread().getName());
        jaula.tomaHamaca(Thread.currentThread().getName());
        tiempo();
        jaula.dejaHamaca(Thread.currentThread().getName());*/
        System.out.println(Thread.currentThread().getName() + " termino");
    }

    private void tiempo(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

