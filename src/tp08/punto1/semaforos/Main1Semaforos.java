package tp08.punto1.semaforos;

public class Main1Semaforos {
    public static void main(String[] args) {
        int cantSoldados = 15, capacidad = 10, cantMostAlmuerzo = 5, cantMostPostres = 3, cantAbridores = 5;
        ComedorSemaforos comedor = new ComedorSemaforos(capacidad, cantMostAlmuerzo, cantAbridores, cantMostPostres);
        Soldado[] soldados = new Soldado[cantSoldados];
        Thread[] hilos = new Thread[cantSoldados];
        for (int i = 0; i < cantSoldados; i++) {
            soldados[i] = new Soldado(comedor);
            hilos[i] = new Thread(soldados[i], "Soldado "+i);
            hilos[i].start();
        }
    }
}
