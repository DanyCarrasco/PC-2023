package tp03.punto4;

public class Area {
    private int cantVisitantes;
    private String nombre;

    public Area(String nombre, int cant) {
        this.nombre = nombre;
        this.cantVisitantes = cant;
    }

    public synchronized void reservarLugar() {
        cantVisitantes = cantVisitantes - 1;
        System.out.println("Se reserva un lugar en el area " + this.nombre + " del parque tematico");
    }

    public synchronized boolean estaDisponibe() {
        return cantVisitantes != 0;
    }

    public synchronized void dejarLugar() {
        cantVisitantes = cantVisitantes + 1;
        System.out.println("Se desocupo un lugar en el area "+ this.nombre + " del parque tematico");
    }

}
