package tp03;

public class CriaturaOscura implements Runnable{
    private Energia energia;

    public CriaturaOscura(Energia energia){
        this.energia = energia;
    }

    public void run(){
        while(energia.getEnergia() > 0){
            energia.consumirEnergia(3);
            System.out.println("Criatura oscura consumio 3 de energia");
            System.out.println("Energia restant CO: " + energia.getEnergia());
        }
    }
}
