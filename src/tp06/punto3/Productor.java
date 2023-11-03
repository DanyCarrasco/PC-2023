public class Productor implements Runnable{
    private BufferMonitores buffer;

    public Productor(BufferMonitores buffer){
        this.buffer = buffer;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+" quiere poner un dato en el buffer");
        buffer.poner();
        System.out.println(Thread.currentThread().getName()+ " termino");
    }
}
