package tp03.punto4;

public class Reserva {
    private int asientosDisponibles;

    public Reserva(int asientosTotales) {
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