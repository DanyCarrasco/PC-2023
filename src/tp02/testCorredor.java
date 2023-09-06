package tp02;

public class testCorredor {
    public static void main(String[] args) {
        int distMayor = 0;
        String nombre = "";
        Corredor[] c = new Corredor[10];
        crearCorredores(c);
        Thread[] arreglo = new Thread[10];
        crearHilos(arreglo, c);
        lanzarHilos(arreglo);
        System.out.println(Thread.currentThread().getName());
    }

    public static void crearCorredores(Corredor[] c){
        for (int i = 0; i < 10; i++) {
            c[i] = new Corredor("#"+i);
        }
    }

    public static void crearHilos(Thread[] arreglo, Corredor[] c){
        for (int i = 0; i < 10; i++) {
            arreglo[i]=new Thread(c[i]);
        }
    }

    public static void lanzarHilos(Thread[] arreglo){
        for (int i = 0; i < 10; i++) {
            arreglo[i].start();
        }
    }
}
