package tp08.punto4.monitores;

public class HemoterapiaMonitores {
    private int cantRevistas, cantCamillasOcupadas;

    public HemoterapiaMonitores() {
        cantRevistas = 4;
        cantCamillasOcupadas = 0;
    }

    public synchronized void ingresarExtraccion() {
        boolean tieneRevista = false;
        if (cantRevistas > 0){
            cantRevistas--;
            tieneRevista = true;
        }
        while (cantCamillasOcupadas == 4) {
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
        cantCamillasOcupadas++;
    }

    public synchronized void dejarExtraccion(){
        cantCamillasOcupadas--;
        this.notifyAll();
    }
}
