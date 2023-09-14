package tp02;

public class UsoHilos {
    //original
    public static void main(String[] args) {
        System.out.println("Hilo principal iniciando.");

        //Primero, construye un objeto unHilo.
        MiHilo mh1 = new MiHilo("#1");
        MiHilo mh2 = new MiHilo("#2");
        MiHilo mh3 = new MiHilo("#3");
        //Luego, construye un hilo de ese objeto.
        Thread nuevoHilo1 = new Thread(mh1);
        Thread nuevoHilo2 = new Thread(mh2);
        Thread nuevoHilo3 = new Thread(mh3);
        //Finalmente, comienza la ejecución del hilo.
        nuevoHilo1.start();
        nuevoHilo2.start();
        nuevoHilo3.start();
        for (int i = 0; i < 50; i++) {
            System.out.print(" .");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException exc) {
            System.out.println("Hilo principal interrumpido.");
        }
        System.out.println("Hilo principal finalizado.");
    }

    /*modificado
    public static void main(String[] args) {
        System.out.println("Hilo principal iniciando.");

        //Primero, construye un hilo de MiHilo2.
        MiHilo2 nuevoHilo = new MiHilo2("#1");
        //Finalmente, comienza la ejecución del hilo.
        nuevoHilo.start();
        for (int i = 0; i < 50; i++) {
            System.out.print(" .");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException exc) {
            System.out.println("Hilo principal interrumpido.");
        }
        System.out.println("Hilo principal finalizado.");
    }*/
}
