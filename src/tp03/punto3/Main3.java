package tp03.punto3;

public class Main3 {
    public static void main(String[] args) {
        int num = 5;
        Hamster[] h = new Hamster[num];
        Thread[] t = new Thread[num];
        Jaula j = new Jaula();
        crearHamsters(h, t, j, num);
        iniciarHamsters(t);
    }

    private static void crearHamsters(Hamster[] h, Thread[] t, Jaula j, int num) {
        for (int i = 0; i < num; i++) {
            h[i] = new Hamster(j);
            t[i] = new Thread(h[i], "Hamster #" + i);
        }
    }

    private static void iniciarHamsters(Thread[] t) {
        for (int i = 0; i < t.length; i++) {
            t[i].start();
        }
    }

}
