package CincoFilosofos;

public class MainFilosofos{
    public static void main(String[] args) {
        Filosofos[] filosofos = new Filosofos[5];
        crearFilosofos(filosofos);
        Thread[] hilosFilosofos = new Thread[5];
        crearHilos(hilosFilosofos, filosofos);
        iniciarHilos(hilosFilosofos);
    }

    public static void crearFilosofos(Filosofos[] f) {
        for (int i = 0; i < f.length; i++) {
            f[i] = new Filosofos(5);
        }
    }

    public static void crearHilos(Thread[] t, Filosofos[] f) {
        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(f[i], "Filosofo "+ i);
        }
    }

    public static void iniciarHilos(Thread[]t) {
        for (int i = 0; i < t.length; i++) {
            t[i].start();
        }
    }
}