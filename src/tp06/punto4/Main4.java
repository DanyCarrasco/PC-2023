package tp06.punto4;

public class Main4 {
    public static void main(String[] args) {
        int limiteBuffer = 2;
        BufferMonitores buffer = new BufferMonitores(limiteBuffer);
        Productor productor = new Productor(buffer);
        Consumidor consumidor = new Consumidor(buffer);
        Thread hiloP = new Thread(productor, "Productor");
        Thread hiloC = new Thread(consumidor,"Consumidor");
        hiloP.start();
        hiloC.start();

    }
}
