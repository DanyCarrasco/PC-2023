import java.util.Random;

public class Hidrogeno extends Thread {
    private Laboratorio laboratorio;

    public Hidrogeno(Laboratorio laboratorio){
        this.laboratorio = laboratorio;
    }

    @Override
    public void run() {
        navegarPorEspacio();
        laboratorio.HListo();
        laboratorio.hacerAgua(true);
    }

    private void navegarPorEspacio(){
        Random rand = new Random();
        int numRandom = 1000 * (rand.nextInt(((9-1)+1)+1) + 1);
        System.out.println("Hidrogeno navega por el espacio");
        try{
        Thread.sleep(numRandom);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Hidrogeno deja de navegar por el espacio");
        }
}