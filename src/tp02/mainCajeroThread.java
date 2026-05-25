package tp02;

// del tp 2 del ejercicio 7 inciso b

public class mainCajeroThread {
    public static void main(String[] args) {
        Cliente2 cliente1 = new Cliente2("Cliente 1", new int[] {2, 2, 1, 5, 2, 3});
        Cliente2 cliente2 = new Cliente2("Cliente 2", new int[] {1, 3, 5, 1, 1});

        //Tiempo inicial de referencia 
        long initialTime = System.currentTimeMillis();
        CajeroThread cajero1 = new CajeroThread("Cajero 1", cliente1, initialTime);
        CajeroThread cajero2 = new CajeroThread("Cajero 2", cliente2, initialTime);

        cajero1.start();
        cajero2.start();
    }
    
}
