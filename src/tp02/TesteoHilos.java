package tp02;

public class TesteoHilos {

    /*original
    public static void main(String[] args) throws InterruptedException {
        Thread miHilo = new MiEjecucion();
        miHilo.start();
        System.out.println("En el main");
    }*/
    //modificado
    public static void main(String[] args) throws InterruptedException {
        Thread miHilo = new MiEjecucion();
        miHilo.start();
        miHilo.join();
        System.out.println("En el main");
    }
}
