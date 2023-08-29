package tp02;

public class MiHilo2 extends Thread{
    public MiHilo2(String nombre){
        super(nombre);
    }
    public void run() {
        System.out.println("Comenzando " + getName());
        try {
            for (int contar = 0; contar < 10; contar++) {
                Thread.sleep(400);
                System.out.println("En " + getName() + ", el recuento " + contar);
            }
        } catch (InterruptedException exc) {
            System.out.println(getName() + " interrumpido.");
        }
        System.out.println("Terminando " + getName());
    }
}
