package tp03.punto4;

public class Area {
    private int asientosDisponibles;

    public Area(int asientosTotales) {
        this.asientosDisponibles = asientosTotales;
    }

    public synchronized boolean realizarReserva(int cantidad) {
        //Emite un comprobante...
        boolean reservaExitosa = false;
        if (cantidad <= asientosDisponibles) {
            asientosDisponibles -= cantidad;
            reservaExitosa = true;
        }
        return reservaExitosa;
    }
}
