package parcial.punto2.semaforos;

import parcial.punto2.locks.Laboratorio;

import java.util.Random;

public class Hidrogeno extends Thread {
    private Laboratorio lab;

    public Hidrogeno(Laboratorio l) {
        this.lab = l;
    }

    @Override
    public void run() {
        navegarPorEspacio();
        lab.HListo();
        lab.hacerAgua(true);
    }

    private void navegarPorEspacio() {
        Random rand = new Random();
        int numRandom = 1000 * (rand.nextInt((9 - 1) + 1) + 1);
        System.out.println("Hidrogeno navega por el espacio por "+numRandom+" milisegundos");
        try {
            Thread.sleep(numRandom);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hidrogeno termino de navegar por el espacio");
    }
}
