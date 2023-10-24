package CincoFilosofos;

public class Filosofos implements Runnable {
    private int cantBocados;

    public Filosofos(int cantBocados) {
        this.cantBocados = cantBocados;
    }

    public void run() {
        int i = 1;
        while (i <= cantBocados) {
            comer(i);
            pensar();
            i++;
        }
        System.out.println(Thread.currentThread().getName()+ " termino su ejecucion");
    }

    private void comer(int i) {
        System.out.println(Thread.currentThread().getName() + " empieza a comer su " + i + " bocado");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " termino de comer su " + i + " bocado");
    }

    private void pensar() {
        System.out.println(Thread.currentThread().getName() + " empieza a pensar");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+ " termino de pensar");
    }

}
