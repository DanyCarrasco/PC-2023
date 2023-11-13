package tp08.punto1.semaforos;

import java.util.concurrent.Semaphore;

public class ComedorSemaforos {
    //private int capacidad, cantMostradorAlmuerzo, cantMostradorPostre, cantAbridores;
    //private Semaphore mutex;
    private Semaphore mostradorAlmuerzo, comedor, mostradorPostre, abridores;

    public ComedorSemaforos(int capacidad, int cantMostradorAlmuerzo, int cantAbridores, int cantMostradorPostre) {
        /*this.capacidad = capacidad;
        this.cantMostradorAlmuerzo = cantMostradorAlmuerzo;
        this.cantAbridores = cantAbridores;
        this.cantMostradorPostre = cantMostradorPostre;*/
        //mutex = new Semaphore(1);
        comedor = new Semaphore(capacidad, true);
        mostradorAlmuerzo = new Semaphore(cantMostradorAlmuerzo);
        abridores = new Semaphore(cantAbridores, true);
        mostradorPostre = new Semaphore(cantMostradorPostre, true);
    }

    public void entraComedor(){
        try{
            System.out.println(Thread.currentThread().getName() + " entra al comedor");
            comedor.acquire();
        }catch(InterruptedException e){
        }
    }

    public void irAlMostrador(){
        try{
            System.out.println(Thread.currentThread().getName() + " esta en el mostradorAlmuerzo");
            mostradorAlmuerzo.acquire();
        }catch(InterruptedException e){
        }
    }

    public void usarAbridor(){
        try{
            abridores.acquire();
        }catch(InterruptedException e){
        }
    }

    public void dejarAbridor(){
        abridores.release();
    }

    public void dejarMostrador(){
        mostradorAlmuerzo.release();
    }

    public void irAlMostradorPostre(){
        try{
            mostradorPostre.acquire();
        }catch(InterruptedException e){
        }
    }

    public void dejarMostradorPostre(){
        mostradorPostre.release();
    }

    public void dejarComedor(){
        comedor.release();
    }
}
