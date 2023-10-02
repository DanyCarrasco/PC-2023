package tp03.punto3;

public class Hamster implements Runnable {
    private Jaula jaula;
    // private boolean plato;// rueda, hamaca;

    public Hamster(Jaula jaula) {
        this.jaula = jaula;
        // this.plato = false;
        /*
         * this.rueda = false;
         * this.hamaca = false;
         */
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ". Listo.");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " va por el plato");
        jaula.usarPlato(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName() + " va por la rueda");
        jaula.usarRueda(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName() + " va por la hamaca");
        jaula.usarHamaca(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName() + " termino");
    }
}
