package tp02;

public class testCorredor {
    public static void main(String[] args) {
        Corredor[] c = new Corredor[10];
        crearCorredores(c);
        Thread[] arreglo = new Thread[10];
        crearHilos(arreglo, c);
        lanzarHilos(arreglo);
        for (int i = 0; i < 10; i++) {
            try {
                arreglo[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        obtenerGanador(c);
    }

    public static void crearCorredores(Corredor[] c) {
        for (int i = 0; i < 10; i++) {
            c[i] = new Corredor("#" + i);
        }
    }

    public static void crearHilos(Thread[] arreglo, Corredor[] c) {
        for (int i = 0; i < 10; i++) {
            arreglo[i] = new Thread(c[i]);
        }
    }

    public static void lanzarHilos(Thread[] arreglo) {
        for (int i = 0; i < 10; i++) {
            arreglo[i].start();
        }
    }

    public static void obtenerGanador(Corredor[] c) {
        Corredor ganador = c[0];
        for (int i = 0; i < 10; i++) {
            if (c[i].getDistRecorrida() > ganador.getDistRecorrida()) {
                ganador = c[i];
            }
        }
        System.out.println("El ganador es: " + ganador.getNombre() + " con una distancia de " + ganador.getDistRecorrida() + " metros");
    }
}
