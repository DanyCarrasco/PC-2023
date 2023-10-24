package punto7;

public class Main7 {
    public static void main(String[] args) {
        int k = 5;
        Confiteria lugar = new Confiteria();
        Empleado[] empleados= new Empleado[k];
        Mozo mozo = new Mozo(lugar, k);
        Thread[] hilosEmpleados= new Thread[k];
        Thread hiloMozo = new Thread(mozo);
        hiloMozo.start();
        for (int i = 0; i < k; i++) {
            empleados[i] = new Empleado(lugar);
            hilosEmpleados[i] = new Thread(empleados[i], "Empleado "+ i);
            hilosEmpleados[i].start();
        }
    }
}
