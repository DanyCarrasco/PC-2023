package tp03.punto4;

public class Visitantes implements Runnable{
    private Area areaParque;
    private int cantidadReserva;

    public Visitantes(Area areaParque, int cantidadReserva) {
        this.areaParque = areaParque;
        this.cantidadReserva = cantidadReserva;
    }

public void run(){
    boolean reservaExitosa = areaParque.realizarReserva(cantidadReserva);
    if (reservaExitosa) {
        System.out.println("Reserva exitosa para " + cantidadReserva + " asientos.");
    } else {
        System.out.println("No hay suficientes asientos disponibles para la reserva de " + cantidadReserva + " asientos.");
    }
}

private void tiempo(){
    try {
        Thread.sleep(5000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
    
}
