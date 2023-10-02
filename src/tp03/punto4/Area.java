package tp03.punto4;

public class Area {
    private int cantVisitantes, ocupados;
    private String nombre;
    private boolean[] lugares;

    public void Area(String nombre, int cant) {
        this.nombre = nombre;
        this.cantVisitantes = cant;
        lugares = new boolean[cant];
        ocupados = 1;
    }

    public synchronized void reservarLugar(int num) {
        ocupados = ocupados + num;
        System.out.println("Se reserva "+ num +" lugares en el area " + this.nombre + " del parque tematico");
    }

    public synchronized boolean estaDisponible(int num) {
        return (cantVisitantes - ocupados) >= num;
    }

    public synchronized void dejarLugar() {
        cantVisitantes = cantVisitantes + 1;
        System.out.println("Se desocupo un lugar en el area "+ this.nombre + " del parque tematico");
    }

    private void iniciarLugares() {
        for (int i = 0; i < lugares.length; i++) {
            lugares[i] = true;
        }
    }
}
