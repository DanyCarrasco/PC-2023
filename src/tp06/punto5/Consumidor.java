package tp06.punto5;

public class Consumidor implements Runnable {
    private BufferMonitores buffer;

    public Consumidor(BufferMonitores buffer) {
        this.buffer = buffer;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " quiere sacar un dato en el buffer");
        buffer.sacar();
        System.out.println(Thread.currentThread().getName() + " termino");
    }
}
