package tpObligatorio;

public class AdministradorAeropuerto implements Runnable{
    private ControlAeropuerto control;

    public AdministradorAeropuerto(ControlAeropuerto aeropuerto) {
        this.control = aeropuerto;
    }

    public void run(){
        control.abrirAeropuerto();
        control.cerrarAeropuerto();
    }
}
