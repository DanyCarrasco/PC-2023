package tp06.punto6;

public class Main6 {
    public static void main(String[] args) {
        int numConsumidor = 1, numProductor = 1;
        BufferMonitores buffer = new BufferMonitores();
        Productor[] productores = new Productor[numProductor];
        Consumidor[] consumidores = new Consumidor[numConsumidor];
        Thread[] hilosP = new Thread[numProductor];
        Thread[] hilosC = new Thread[numConsumidor];

        for (int index = 0; index < numProductor; index++) {
            productores[index] = new Productor(buffer);
            hilosP[index] = new Thread(productores[index], "Productor " + index);
            hilosP[index].start();
        }

        for (int i = 0; i < numConsumidor; i++) {
            consumidores[i] = new Consumidor(buffer);
            hilosC[i] = new Thread(consumidores[i], "Consumidor " + i);
            hilosC[i].start();
        }
    }
}
