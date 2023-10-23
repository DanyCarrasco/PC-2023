package punto4;

public class Main4 {
    public static void main(String[] args) {
        GestorImpresoras gestor = new GestorImpresoras(3);
        Cliente[] clientes = new Cliente[10];
        Thread[] hilos = new Thread[10];
        for (int i = 0; i < 10; i++) {
            clientes[i] = new Cliente(gestor, i);
            hilos[i] = new Thread(clientes[i]);
            hilos[i].start();
        }
    }
}
