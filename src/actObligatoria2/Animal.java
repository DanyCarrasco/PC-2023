package actObligatoria2;

public class Animal implements Runnable{
    private String tipo;
    private String nombre;
    private Comedor comedor;

    public Animal(String tipo, String nombre, Comedor comedor) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.comedor = comedor;
    }
    //No escribe en el run
    public void run() {
        try {
            System.out.println("El " + tipo + " " + nombre + " quiere entrar");
            comedor.entrar(tipo);
            comer();
            comedor.salir(tipo);
            System.out.println("El " + tipo + " " + nombre + " salio");
        } catch (InterruptedException e) {

        }
    }

    private void comer(){
        System.out.println("El " + tipo + " " + nombre + " entro y esta comiendo");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("El " + tipo + " " + nombre + " termino de comer");
    }
}
