package tp06.punto2;

public class ControlSalaEstudio {
    private int maximo, cantidad;

    public ControlSalaEstudio(int limite) {
        this.maximo = limite;
        this.cantidad = 0;
    }

    public synchronized void ingresar() {
        while (cantidad == maximo) {
            System.out.println(Thread.currentThread().getName()+" SIGUE ESPERANDO...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        cantidad++;
    }

    public synchronized void salir(){
        cantidad--;
        this.notifyAll();
    }
}
