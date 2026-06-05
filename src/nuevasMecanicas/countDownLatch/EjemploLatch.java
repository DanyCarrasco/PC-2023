package nuevasMecanicas.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class EjemploLatch {
    public static void main(String[] args) throws InterruptedException {
        // Se inicializa el contador en 3 (para 3 trabajadores)`
        CountDownLatch latch = new CountDownLatch(3);

        // Crear y lanzar tres hilos trabajadores
        for (int i = 1; i <= 3; i++) {
            new Thread(new Worker(i, latch)).start();
        }

        System.out.println("El hilo principal está esperando a los trabajadores...");
        
        // El hilo principal se bloquea aquí hasta que el contador sea 0
        latch.await();

        System.out.println("Todos los trabajadores terminaron. El hilo principal continúa.");
    }
}
class Worker implements Runnable {
    private final int id;
    private final CountDownLatch latch;

    public Worker(int id, CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println("Trabajador " + id + " iniciando tarea.");
            Thread.sleep((long) (Math.random() * 2000)); // Simula trabajo
            System.out.println("Trabajador " + id + " finalizó su tarea.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Decrementa el contador del latch de forma segura
            latch.countDown(); 
        }
    }
}