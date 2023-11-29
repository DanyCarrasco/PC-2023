package parcial.punto2.locks;

public class Oxigeno extends Thread{
    private Laboratorio lab;

    public Oxigeno(Laboratorio l) {
        this.lab = l;
    }

    @Override
    public void run() {
        navegarPorEspacio();
        lab.OListo();
        lab.hacerAgua(false);
    }

    private void navegarPorEspacio() {
        System.out.println("Oxigeno navega por el espacio");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Oxigeno termino de navegar por el espacio");
    }
}
