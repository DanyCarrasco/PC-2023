import java.util.Random;

public class Oxigeno extends Thread {
    private Laboratorio laboratorio;

    public Oxigeno(Laboratorio laboratorio){
        this.laboratorio = laboratorio;
    }

    @Override
    public void run() {
        navegarPorEspacio();
        laboratorio.OListo();
        laboratorio.hacerAgua(false);
    }

    private void navegarPorEspacio(){
        Random rand = new Random();
        int numRandom = 1000 * (rand.nextInt(((9-1)+1)+1) + 1);
        System.out.println("Oxigeno navega por el espacio");
        try {
            Thread.sleep(numRandom);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Oxigeno deja de navegar por el espacio");
        }
}