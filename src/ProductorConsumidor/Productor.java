package ProductorConsumidor;

public class Productor implements Runnable {
    private Buffer buffer;

    public Productor(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        while (true) {
            int x = 1;
            try {
                this.buffer.agregarItem(x);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x++;
        }
    }


}
