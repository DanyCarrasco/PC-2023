package tp08.punto1.semaforos;

import java.util.Random;

public class Soldado implements Runnable {
    private ComedorSemaforos comedor;
    private boolean tieneBotella, quierePostre;

    public Soldado(ComedorSemaforos comedor) {
        Random rd = new Random();
        this.comedor = comedor;
        this.tieneBotella = rd.nextBoolean();
        this.quierePostre = rd.nextBoolean();
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " quiere entrar al comedor");
        comedor.entraComedor();
        System.out.println(Thread.currentThread().getName() + " quiere ir al mostradorAlmuerzo");
        comedor.irAlMostrador();
        estaEnMostrador();
        if(tieneBotella){
            System.out.println(Thread.currentThread().getName() + " quiere usar un abridor de botellas");
            comedor.usarAbridor();
            usaAbridor();
            comedor.dejarAbridor();
        }
        System.out.println(Thread.currentThread().getName() + " deja el mostradorAlmuerzo");
        comedor.dejarMostrador();
        if (quierePostre){
            System.out.println(Thread.currentThread().getName() + " quiere ir al mostradorPostre");
            comedor.irAlMostradorPostre();
            estaEnMostradorPostre();
            comedor.dejarMostradorPostre();
        }
        System.out.println(Thread.currentThread().getName() + " termino de comer, deja la bandeja y sale del comedor");
        comedor.dejarComedor();
    }

    private void estaEnMostrador() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {

        }
    }

    private void usaAbridor() {
        System.out.println(Thread.currentThread().getName() + " usa el abridor de botellas");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        System.out.println(Thread.currentThread().getName() + " deja el abridor de botellas");
    }

    private void estaEnMostradorPostre() {
        System.out.println(Thread.currentThread().getName() + " esta en el mostradorPostre");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {

        }
        System.out.println(Thread.currentThread().getName() + " deja el mostradorPostre");
    }
}
