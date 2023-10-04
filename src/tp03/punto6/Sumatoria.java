package tp03.punto6;

public class Sumatoria {
    private int total;

    public Sumatoria(){
        this.total = 0;
    }

    public synchronized void sumar(int valor){
        this.total += valor;
    }

    public int getTotal(){
        return this.total;
    }
}
