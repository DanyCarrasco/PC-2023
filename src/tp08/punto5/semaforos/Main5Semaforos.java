package tp08.punto5.semaforos;

public class Main5Semaforos {
    public static void main(String[] args) {
        int cantRaciones = 5, cantCanibales = 10;
        OllaSemaforos ollaComunitaria = new OllaSemaforos(cantRaciones);
        Canibal[] canibales = new Canibal[cantCanibales];
        Thread[] hilos = new Thread[cantCanibales];
        Thread cocinero = new Thread((new CocineroTribu(ollaComunitaria)));
        for (int i = 0; i < cantCanibales; i++) {
            canibales[i] = new Canibal(ollaComunitaria);
            hilos[i] = new Thread(canibales[i], "Canibal "+ i);
            hilos[i].start();
        }
        cocinero.start();
    }
}
