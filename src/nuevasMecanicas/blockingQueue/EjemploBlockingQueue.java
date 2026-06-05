package nuevasMecanicas.blockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EjemploBlockingQueue {
    public static void main(String[] args) {
        // Creamos una cola con capacidad máxima de 5 elementos
        BlockingQueue<Integer> colaCompartida = new LinkedBlockingQueue<>(5);

        // Hilo Productor
        Thread productor = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    System.out.println("Producido: " + i);
                    colaCompartida.put(i); // Se bloquea automáticamente si la cola se llena
                    Thread.sleep(200); // Simula tiempo de trabajo
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Hilo Consumidor
        Thread consumidor = new Thread(() -> {
            try {
                while (true) {
                    Integer elemento = colaCompartida.take(); // Se bloquea automáticamente si está vacía
                    System.out.println("Consumido: " + elemento);
                    if (elemento == 10)
                        break; // Criterio de parada
                    Thread.sleep(500); // Simula tiempo de procesamiento lento
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        productor.start();
        consumidor.start();
    }
}
