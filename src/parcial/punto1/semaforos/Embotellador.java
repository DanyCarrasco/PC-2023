package parcial.punto1.semaforos;

public class Embotellador extends Thread{
    private PlantaEmbotelladora planta;
    private boolean agua;
    public Embotellador(PlantaEmbotelladora p, boolean agua){
        planta = p;
        this.agua = agua;
    }

    public void run(){
        while(true){
            preparaBotella();
            if (agua){
                System.out.println(Thread.currentThread().getName()+" quiere guardar una botella de agua en caja");
            }else {
                System.out.println(Thread.currentThread().getName()+" quiere guardar una botella de vino en caja");
            }
            planta.guardarBotella(agua);
        }
    }

    private void preparaBotella(){
        try{
            System.out.println("Preparando botella");
            Thread.sleep(1000);
            System.out.println("Botella lista");
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
