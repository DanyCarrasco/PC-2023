package tp08.punto5.locks;

public class Main5Locks {
    public static void main(String[] args) {
        int numRaciones = 5, cantCanibales = 10;
        OllaLocks olla = new OllaLocks(numRaciones);
        Thread cocinero = new Thread((new CocineroTribu(olla)), "Cocinero");
        Canibal[] canibales = new Canibal[cantCanibales];
        Thread[] hilos = new Thread[cantCanibales];
        for (int i = 0; i < cantCanibales; i++) {
            canibales[i] = new Canibal(olla);
            hilos[i] = new Thread(canibales[i], "Canibal " + i);
            hilos[i].start();
        }
        cocinero.start();
    }
}
