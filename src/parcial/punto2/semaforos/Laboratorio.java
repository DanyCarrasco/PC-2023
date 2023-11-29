package parcial.punto2.semaforos;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Laboratorio {
    private Semaphore mutexOxigeno;
    private Semaphore mutexHidrogeno;
    private Semaphore paraAgua;
    private int cantOParaAgua;
    private int cantHParaAgua;
    private int numHacerAgua, numParaRecipiente;

    public Laboratorio(int cantParaRecipiente) {
        mutexHidrogeno = new Semaphore(2,true);
        mutexOxigeno = new Semaphore(1,true);
        paraAgua = new Semaphore(0,true);
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
        if (atomoHidrogeno) {
            casoH();
        } else {
            casoO();
        }
    }

    private void casoH() {
        try {
            mutexHidrogeno.acquire();
            cantHParaAgua++;
            if (cantHParaAgua == 2 && cantOParaAgua == 1) {
                haciendoAgua();
            } else {
                mutexHidrogeno.release();
                paraAgua.acquire();
                mutexHidrogeno.acquire();
            }
            mutexHidrogeno.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void casoO() {
        try {
            mutexOxigeno.acquire();
            cantOParaAgua++;
            if (cantHParaAgua == 2 && cantOParaAgua == 1) {
                haciendoAgua();
            } else {
                mutexOxigeno.release();
                paraAgua.acquire();
                mutexOxigeno.acquire();
            }
            mutexOxigeno.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void haciendoAgua() {
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
        paraAgua.release(2);
        mutexOxigeno.release();
        mutexHidrogeno.release(2);
    }
}
