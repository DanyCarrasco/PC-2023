package tp03;

public class Sanador implements Runnable{
    private Energia energia;

    public Sanador(Energia energia){
        this.energia = energia;
    }

    public void run(){
        while(energia.getEnergia() > 0){
            energia.recargarEnergia(3);
            System.out.println("Sanador recargo 3 de energia");
            System.out.println("Energia restante S: " + energia.getEnergia());
        }
    }

}
