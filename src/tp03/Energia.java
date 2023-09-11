package tp03;

public class Energia {
    private int energia = 10;

    public Energia() {
    }

    public int getEnergia() {
        return energia;
    }

    public synchronized void recargarEnergia(int cant) {
        this.energia = this.energia + cant;
    }

    public synchronized void consumirEnergia(int consumo){
        energia = energia - consumo;
    }
}
