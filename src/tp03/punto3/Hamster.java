package tp03.punto3;

public class Hamster implements Runnable {
    private Jaula jaula;

    public Hamster(Jaula j) {
        jaula = new Jaula();
    }

    @Override
    public void run() {
        jaula.usarPlato(Thread.currentThread().getName());
        jaula.usarRueda(Thread.currentThread().getName());
        jaula.usarHamaca(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName() + " termino");
    }
}

