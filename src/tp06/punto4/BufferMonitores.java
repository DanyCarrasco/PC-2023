package tp06.punto4;
public class BufferMonitores {
    int maximo, cantidad;

    public BufferMonitores(int limite){
        this.maximo = limite;
        this.cantidad = 0;
    }

    public synchronized void poner(){
        while (cantidad == maximo) {
            System.out.println(Thread.currentThread().getName()+ " SIGUE ESPERANDO...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(Thread.currentThread().getName()+" pone un elemento en el buffer");
        cantidad++;
        System.out.println("En el buffer hay "+ cantidad);
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
        System.out.println("En el buffer hay "+ cantidad);
        this.notifyAll();
    }
}
