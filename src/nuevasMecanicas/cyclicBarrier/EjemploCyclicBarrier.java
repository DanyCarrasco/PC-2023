package nuevasMecanicas.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;


public class EjemploCyclicBarrier {

    public static void main(String[] args) {
        int numeroDeAutos = 3;

        // Se define la acción de barrera que se ejecuta cuando todos los hilos llegan
        Runnable accionInicio = () -> System.out.println("\n[SISTEMA] ¡Todos los autos listos! ¡ARRANCAN! 🏁\n");

        // Creamos la barrera para 3 hilos y le pasamos la acción intermedia
        CyclicBarrier barrera = new CyclicBarrier(numeroDeAutos, accionInicio);

        // Creamos y lanzamos los hilos de los autos
        for (int i = 1; i <= numeroDeAutos; i++) {
            String nombreAuto = "Auto #" + i;
            new Thread(new Auto(nombreAuto, barrera)).start();
        }
    }

    // Clase que representa el hilo de cada participante
    static class Auto implements Runnable {
        private final String nombre;
        private final CyclicBarrier barrera;

        public Auto(String nombre, CyclicBarrier barrera) {
            this.nombre = nombre;
            this.barrera = barrera;
        }

        @Override
        public void run() {
            try {
                // Simula el tiempo que tarda el auto en prepararse o llegar a la pista
                System.out.println(nombre + " se está ubicando en la grilla de partida...");
                Thread.sleep((long) (Math.random() * 2000));
                
                System.out.println(nombre + " ¡Listo en la línea de salida!");
                
                // El hilo se detiene aquí y espera a los demás
                barrera.await();

                // Esta línea solo se ejecuta después de que la barrera se libera
                System.out.println(nombre + " aceleró a fondo y está corriendo. 🏎️💨");

            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println(nombre + " tuvo un problema mecánico en la barrera.");
            }
        }
    }
}
