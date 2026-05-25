package tp02;

// del tp 2, ejercicio 8

public class mainCajeroRunnable {
    public static void main(String[] args) {
        Cliente2 cliente1 = new Cliente2("Cliente 1", new int[] {2, 2, 1, 5, 2, 3});
        Cliente2 cliente2 = new Cliente2("Cliente 2", new int[] {1, 3, 5, 1, 1});

        //Tiempo inicial de referencia 
        long initialTime = System.currentTimeMillis();
        CajeroRunnable cajero1 = new CajeroRunnable("Cajero 1", cliente1, initialTime);
        CajeroRunnable cajero2 = new CajeroRunnable("Cajero 2", cliente2, initialTime);

        Thread h1 = new Thread(cajero1);
        Thread h2 = new Thread(cajero2);

        h1.start();
        h2.start();
    }
}
