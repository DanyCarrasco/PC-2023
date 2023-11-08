package tp06.punto6;
public class BufferMonitores {
    int maximo, cantidad;

    public BufferMonitores(){
        this.cantidad = 0;
    }

    public synchronized void poner(){
        System.out.println(Thread.currentThread().getName()+" pone un elemento en el buffer");
        cantidad++;
        this.notifyAll();
    }

    public synchronized void sacar(){
        while (cantidad == 0) {
            System.out.println(Thread.currentThread().getName()+ " SIGUE ESPERANDO...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(Thread.currentThread().getName()+" saca un elemento en el buffer");
        cantidad--;
        this.notifyAll();
    }
}
