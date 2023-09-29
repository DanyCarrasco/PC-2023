package tp03.punto3;

public class Hamster implements Runnable {
    private Jaula jaula;
    private boolean plato, rueda, hamaca;

    public Hamster() {
        jaula = new Jaula();
        this.plato = false;
        this.rueda = false;
        this.hamaca = false;
    }

    @Override
    public void run() {
        while(!plato || !rueda || !hamaca){
            if(jaula.usarPlato(Thread.currentThread().getName())){
                plato = true;
            } else {
                System.out.println(Thread.currentThread().getName()+ " no puede usar el plato");
            }

            if(jaula.usarRueda(Thread.currentThread().getName())){
                rueda = true;
            } else {
                System.out.println(Thread.currentThread().getName()+ " no puede usar la rueda");
            }

            if(jaula.usarHamaca(Thread.currentThread().getName())){
                hamaca = true;
            } else {
                System.out.println(Thread.currentThread().getName()+ " no puede usar la hamaca");
            }
        }
        System.out.println(Thread.currentThread().getName() + " termino");
    }
} 

