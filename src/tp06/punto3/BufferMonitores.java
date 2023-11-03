public class BufferMonitores {
    int maximo, cantidad;

    public BufferMonitores(int limite){
        this.maximo = limite;
        this.cantidad = 0;
    }

    public synchronized void poner(){
        while (cantidad == maximo) {
            System.out.println(Thread.currentThread().getName()+ " SIGUE ESPERANDO...");
            this.wait();
        }
        System.out.println(Thread.currentThread().getName()+" pone un elemento en el buffer");
        cantidad++;
        this.notifyAll();
    }

    public synchronized void sacar(){
        while (cantidad == 0) {
            System.out.println(Thread.currentThread().getName()+ " SIGUE ESPERANDO...");
            this.wait();
        }
        System.out.println(Thread.currentThread().getName()+" saca un elemento en el buffer");
        cantidad--;
        this.notifyAll();
    }
}
