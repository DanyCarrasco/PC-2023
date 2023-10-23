package punto5;

public class Cliente implements Runnable {
    private GestorImpresoras gestor;
    private boolean imprimio;
    private String tipoImpresion;

    public Cliente(GestorImpresoras gestor, int id, String tipoImpresion) {
        this.gestor = gestor;
        this.imprimio = false;
        this.tipoImpresion = tipoImpresion;
    }

    public void run() {
        while (!imprimio) {
            System.out.println(Thread.currentThread().getName() + " quiere imprimir");
            Impresora impresora = gestor.usarImpresora(this.tipoImpresion);
            if (impresora != null) {
                //System.out.println(Thread.currentThread().getName() + " usa una impresora");
                impresora.imprimir(Thread.currentThread().getName(), this.tipoImpresion);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                impresora.terminar(Thread.currentThread().getName(),this.tipoImpresion);
                //System.out.println(Thread.currentThread().getName() + " deja la impresora");
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
