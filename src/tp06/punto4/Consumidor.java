package tp06.punto4;

public class Consumidor implements Runnable{
    private BufferMonitores buffer;

    public Consumidor(BufferMonitores buffer){
        this.buffer = buffer;
    }

    public void run(){
        while (true){
            System.out.println(Thread.currentThread().getName()+" quiere sacar un dato en el buffer");
            buffer.sacar();
        }
        //System.out.println(Thread.currentThread().getName()+ " termino");
    }
}
