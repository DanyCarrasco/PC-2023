package tp02;

public class ThreadEjemplo extends Thread{
    public ThreadEjemplo(String str){
        super(str);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++){
            System.out.println(i + " " + getName());
            System.out.println("Termina thread " + getName());
        }
    }

    /*original
    public static void main(String[] args) {
        new ThreadEjemplo("Maria Jose").start();
        new ThreadEjemplo("Jose Maria").start();
        System.out.println("Termina thread main");
    }*/

    public static void main(String[] args) {
        ThreadEjemplo2 run1 = new ThreadEjemplo2();
        ThreadEjemplo2 run2 = new ThreadEjemplo2();
        new Thread(run1,"Maria Jose").start();
        new Thread(run2,"Jose Maria").start();
        System.out.println("Termina thread main");
    }
}
