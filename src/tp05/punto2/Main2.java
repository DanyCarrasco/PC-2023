package tp05.punto2;

public class Main2 {
    public static void main(String[] args) {
        int k = 5;
        Confiteria lugar = new Confiteria();
        Empleado[] empleados = new Empleado[k];
        Mozo mozo = new Mozo(lugar, k);
        Cocinero cocinero = new Cocinero(lugar);
        Thread[] hilosEmpleados = new Thread[k];
        Thread hiloMozo = new Thread(mozo);
        Thread hiloCocinero = new Thread(cocinero);
        hiloCocinero.start();
        hiloMozo.start();
        for (int i = 0; i < k; i++) {
            empleados[i] = new Empleado(lugar);
            hilosEmpleados[i] = new Thread(empleados[i], "Empleado " + i);
            hilosEmpleados[i].start();
        }
    }
}
