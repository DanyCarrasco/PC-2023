package tp04;

public class Cliente implements Runnable {
    private GestorImpresoras gestor;
    private boolean imprimio;

    public Cliente(GestorImpresoras gestor, int id) {
        this.gestor = gestor;
        this.imprimio = false;
    }

    public void run() {
        while (!imprimio) {
            System.out.println(Thread.currentThread().getName() + " quiere imprimir");
            Impresora impresora = gestor.usarImpresora();
            if (impresora != null) {
                System.out.println(Thread.currentThread().getName() + " usa una impresora");
                impresora.imprimir(Thread.currentThread().getName());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                impresora.terminar(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " deja la impresora");
                gestor.dejarImpresora();
                imprimio = true;
            } else {
                System.out.println(Thread.currentThread().getName()+ " no pudo imprimir espera un poco");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
