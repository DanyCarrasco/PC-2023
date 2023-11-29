package parcial.punto2.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Laboratorio {
    private Lock mutex;
    private Condition HEsperando;
    private Condition OEsperando;
    private Condition paraAgua;
    private int cantOParaAgua;
    private int cantHParaAgua;
    private int numHacerAgua, numParaRecipiente;

    public Laboratorio(int cantParaRecipiente) {
        mutex = new ReentrantLock();
        HEsperando = mutex.newCondition();
        OEsperando = mutex.newCondition();
        paraAgua = mutex.newCondition();
        cantOParaAgua = 0;
        cantHParaAgua = 0;
        numHacerAgua = 0;
        numParaRecipiente = cantParaRecipiente;
    }

    public void HListo() {
        System.out.println("Hidrogeno listo para hacer agua");
    }

    public void OListo() {
        System.out.println("Oxigeno listo para hacer agua");
    }

    public void hacerAgua(boolean atomoHidrogeno) {
        mutex.lock();
        if (atomoHidrogeno) {
            casoH();
        } else {
            casoO();
        }
        mutex.unlock();
    }

    private void casoH() {
        try {
            while (cantHParaAgua == 2) {
                HEsperando.await();
            }
            cantHParaAgua++;
            if (cantHParaAgua == 2 && cantOParaAgua == 1) {
                haciendoAgua();
            } else {
                paraAgua.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void casoO() {
        try {
            while (cantOParaAgua == 1) {
                OEsperando.await();
            }
            cantOParaAgua++;
            if (cantHParaAgua == 2 && cantOParaAgua == 1) {
                haciendoAgua();
            } else {
                paraAgua.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void haciendoAgua() {
        paraAgua.signalAll();
        System.out.println("Se hizo agua y se deposita en el recipiente");
        numHacerAgua++;
        System.out.println("Se hizo " + numHacerAgua + " de agua");

        if (numHacerAgua == numParaRecipiente) {
            System.out.println("Se lleno el recipiente");
            System.out.println("Agua es envasada y se vacia el recipiente");
            numHacerAgua = 0;
        }

        cantHParaAgua = 0;
        cantOParaAgua = 0;
        HEsperando.signalAll();
        OEsperando.signalAll();
    }
}
