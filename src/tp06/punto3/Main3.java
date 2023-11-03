public class Main3 {
    public static void main(String[] args) {
        int numConsumidor = 3, numProductor=3, limiteBuffer =2;
        BufferMonitores buffer = new BufferMonitores(limiteBuffer);
        Productor[] productores = new Productor[numProductor];
        Consumidor[] consumidores = new Consumidor[numConsumidor];
        Thread[] hilosP = new Thread[numProductor];
        Thread[] hilosC = new Thread[numConsumidor];

        for (int index = 0; index < numProductor; index++) {
            productores[index] = new Productor(buffer);
            hilosP[index] = new Thread(productores[index], "Productor "+i);
            hilosP[index].start();
        }

        for (int i = 0; i < numConsumidor; i++) {
            consumidores[i] = new Consumidor(buffer);
            hilosC = new Thread(consumidores[i], "Consumidor "+i);
            hilosC[i].start();
        }
    }
}
