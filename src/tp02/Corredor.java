package tp02;

public class Corredor implements Runnable{
    private String nombre;
    private int distRecorrida;

    public Corredor(String nombre) {
        this.nombre = nombre;
        distRecorrida = 0;
    }

    public String getNombre(){
        return nombre;
    }

    public int getDistRecorrida(){
        return distRecorrida;
    }

    public void run(){
        while (distRecorrida <= 100) {
            System.out.println("Corredor "+nombre+" recorrio una distancia de " + distRecorrida+ " pasos");
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e) {
                System.out.println("Corredor "+nombre +" interrumpido");
            }
            distRecorrida = distRecorrida + (int) (Math.random() * 10) + 1;
        }
        System.out.println("Corredor "+ nombre + " termino, haciendo una distancia de "+ distRecorrida);
    }
}
