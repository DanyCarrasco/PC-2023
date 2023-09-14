package tp03;

public class Sanador implements Runnable{
    private Energia energia;
    private int cantTurnos = 0;

    public Sanador(Energia energia){
        this.energia = energia;
    }

    public void run(){
        while(cantTurnos < 5) {
            energia.recargarEnergia(3, Thread.currentThread().getName());
            cantTurnos++;
        }
    }

}
