package tp03;



public class Main1 {
    public static void main(String[] args) {
        VerificarCuenta vc = new VerificarCuenta();
        Thread Luis = new Thread(vc, "Luis");
        Thread Manuel = new Thread(vc, "Manuel");
        Luis.start();
        Manuel.start();
        }
}
