import java.util.concurrent.Semaphore;

public class Laboratorio {
    public int cantOParaAgua;
    public int cantHParaAgua;
    public int numHacerAgua, numParaRecipiente;

    public Semaphore mutex, paraAgua;
    public Semaphore OParaAgua;
    public Semaphore HParaAgua;

    public Laboratorio(int cantParaRecipiente) {
        this.cantOParaAgua = 0;
        this.cantHParaAgua = 0;
        this.numHacerAgua = 0;
        this.numParaRecipiente = cantParaRecipiente;

        this.mutex = new Semaphore(1);
        this.OParaAgua = new Semaphore(0);
        this.HParaAgua = new Semaphore(0);
        this.paraAgua = new Semaphore(0);
    }

    public void HListo() {
        System.out.println("Hidrogeno listo para hacer agua");
    }

    public void OListo() {
        System.out.println("Oxigeno listo para hacer agua");
    }

    public void hacerAgua(boolean esHidrogeno) {
        if (esHidrogeno) {
            casoH();
        } else {
            casoO();
        }
    }

    private void casoH() {
        try {
            mutex.acquire();
            cantHParaAgua++;

            if (numHacerAgua >= 2 && cantOParaAgua >= 1) {
                cantHParaAgua = cantHParaAgua - 2;
                cantOParaAgua = cantOParaAgua - 1;
                HParaAgua.release(2);
                OParaAgua.release(1);numHacerAgua++;
            }
            mutex.release();

            HParaAgua.acquire();
            haciendoAgua();
            paraAgua.acquire();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void casoO() {
        try {
            mutex.acquire();
            cantOParaAgua++;

            if (cantOParaAgua >= 1 && cantHParaAgua >= 2) {
                cantHParaAgua = cantHParaAgua - 2;
                cantOParaAgua = cantOParaAgua - 1;
                HParaAgua.release(2);
                OParaAgua.release(1);
            }
            mutex.release();

            OParaAgua.acquire();
            haciendoAgua();
            paraAgua.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void haciendoAgua() {
        try {
            mutex.acquire();
            numHacerAgua++;
            System.out.println("Se hizo agua y se pone en el recipiente");

            if (numHacerAgua == numParaRecipiente) {
                System.out.println("Se lleno el recipiente");
                numHacerAgua = 0;
                System.out.println("Se vacio el recipiente y se envasa el agua");
            }
            paraAgua.release(3);
            mutex.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}