package ProductorConsumidor;

public class Consumidor implements Runnable{
    private Buffer buffer;
    public Consumidor(Buffer buffer){
        this.buffer = buffer;
    }

    public void run(){
        while(true){
            try {
                this.buffer.sacarItem();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
