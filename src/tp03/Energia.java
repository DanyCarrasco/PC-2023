package tp03;

public class Energia {
    private int energia = 5;

    public Energia() {
    }

    public synchronized int getEnergia() {
        return energia;
    }

    public synchronized void recargarEnergia(int cant, String nombre) {
        this.energia = this.energia + cant;
        System.out.println(nombre + " recargo " + cant + " de energia");
        System.out.println("Energia restante: " + energia);
    }

    public synchronized void consumirEnergia(int consumo, String nombre){
        energia = energia - consumo;
        System.out.println(nombre + " consumio " + consumo + " de energia");
        System.out.println("Energia restante: " + energia);
    }
}
