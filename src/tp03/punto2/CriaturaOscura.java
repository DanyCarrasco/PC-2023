package tp03.punto2;

public class CriaturaOscura implements Runnable{
    private Energia energia;
    private int cantTurnos = 0;

    public CriaturaOscura(Energia energia){
        this.energia = energia;
    }

    public void run(){
        while(cantTurnos < 5){
            energia.consumirEnergia(3, Thread.currentThread().getName());
            cantTurnos++;
        }
    }
}
