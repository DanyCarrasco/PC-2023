package tp08.punto4.monitores;

public class HemoterapiaMonitores {
    private int cantRevistas, cantCamillasDisponible;

    public HemoterapiaMonitores() {
        cantRevistas = 9;
        cantCamillasDisponible = 0;
    }

    public synchronized void ingresarExtraccion() {
        boolean tieneRevista = false;
        if (cantRevistas > 0){
            cantRevistas--;
            tieneRevista = true;
        }
        while (cantCamillasDisponible == 4) {
            if (tieneRevista) {
                System.out.println(Thread.currentThread().getName() + " ESPERA mirando una revista");
            } else {
                System.out.println(Thread.currentThread().getName() + " ESPERA mirando la tv");
            }
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (tieneRevista){
            cantRevistas++;
        }
        cantCamillasDisponible++;
    }

    public synchronized void dejarExtraccion(){
        cantCamillasDisponible--;
        this.notifyAll();
    }
}
