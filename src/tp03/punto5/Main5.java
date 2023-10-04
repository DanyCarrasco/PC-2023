package tp03.punto5;

public class Main5 {
    public static void main(String[] args) {
        Surtidor surtidor = new Surtidor(100);

        Thread Auto1 = new Thread(new Auto("1", 50, surtidor));
        Thread Auto2 = new Thread(new Auto("2", 30, surtidor));
        Thread Auto3 = new Thread(new Auto("3", 40,surtidor));

        Auto1.start();
        Auto2.start();
        Auto3.start();
    }
}
