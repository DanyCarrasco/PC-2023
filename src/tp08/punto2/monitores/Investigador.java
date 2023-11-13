package tp08.punto2.monitores;

public class Investigador implements Runnable{
    private SalaMonitores sala;
    public Investigador(SalaMonitores sala){
        this.sala = sala;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+" quiere entrar al observatorio");
        sala.ingresaInvestigador();
        System.out.println(Thread.currentThread().getName()+" entra al observatorio");
        analizar();
        System.out.println(Thread.currentThread().getName()+" deja su observacion en el libro y sale del observatorio");
        sala.saleInvestigador();
    }

    private void analizar(){
        System.out.println(Thread.currentThread().getName()+" empieza a analizar las estrellas");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName()+" termino de analizar las estrellas");
    }
}
