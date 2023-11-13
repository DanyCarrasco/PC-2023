package tp08.punto2.monitores;

import java.util.Random;

public class Visitante implements Runnable{
    private SalaMonitores sala;
    private boolean tieneSilla;
    public Visitante(SalaMonitores sala){
        Random rd = new Random();
        this.sala = sala;
        tieneSilla = rd.nextBoolean();
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+" quiere ingresar al observatorio");
        sala.ingresaVisitante(tieneSilla);
        System.out.println(Thread.currentThread().getName()+" ingresa al observatorio");
        estudiarEstrellas();
        System.out.println(Thread.currentThread().getName()+" sale del observatorio");
        sala.saleVisitante(tieneSilla);
    }

    private void estudiarEstrellas(){
        System.out.println(Thread.currentThread().getName()+" empieza a estudiar las estrellas");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName()+" termino de estudiar las estrellas");
    }
}
