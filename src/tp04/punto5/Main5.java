package punto5;

public class Main5 {
    public static void main(String[] args) {
        int cantClientes = 6;
        GestorImpresoras gestor = new GestorImpresoras(2);
        Cliente[] clientes = new Cliente[cantClientes];
        Thread[] hilos = new Thread[cantClientes];
        String tipo = "";
        int j = 2;
        for (int i = 0; i < cantClientes; i++) {
            if (j == i) {
                tipo = "x";
                j = j + 3;
            } else {
                if (i % 2 == 0) {
                    tipo = "a";
                } else {
                    tipo = "b";
                }
            }
            clientes[i] = new Cliente(gestor, i, tipo);
            hilos[i] = new Thread(clientes[i]);
            hilos[i].start();
        }
    }
}
