package tp02;

public class MiHilo implements Runnable {
    String nombreHilo;

    public MiHilo(String nombre) {
        nombreHilo = nombre;
    }

    //Punto de entrada del hilo
    //Los hilos comienzan a ejecutarse aquí
    @Override
    public void run() {
        System.out.println("Comenzando " + nombreHilo);
        try {
            for (int contar = 0; contar < 10; contar++) {
                Thread.sleep(400);
                System.out.println("En " + nombreHilo + ", el recuento " + contar);
            }
        } catch (InterruptedException exc) {
            System.out.println(nombreHilo + " interrumpido.");
        }
        System.out.println("Terminando " + nombreHilo);
    }
}
