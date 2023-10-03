package tp03.punto5;

public class Surtidor {
    private int capacidad;
    private int combustibleDisponible;

    public Surtidor(int capacidad) {
        this.capacidad = capacidad;
        this.combustibleDisponible = capacidad;
    }

    public synchronized boolean cargarCombustible(int cantidad) {
        boolean cargaExitosa = false;
        if (cantidad <= combustibleDisponible) {
            combustibleDisponible -= cantidad;
            cargaExitosa = true;
        }
        System.out.println("Estado del Surtidor: tiene " + combustibleDisponible + " litros disponibles");
        return cargaExitosa;
    }
}
