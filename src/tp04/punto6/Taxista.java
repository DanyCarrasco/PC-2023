package punto6;

public class Taxista implements Runnable{
    private Taxi taxi;

    public Taxista(Taxi taxi){
        this.taxi = taxi;
    }

    public void run(){
        this.taxi.esperarSolicitud();
        System.out.println("Taxista despierta y empieza el viaje hacia el destino");
        realizarViaje();
        System.out.println("Taxista llega al destino y espera su paga");
        this.taxi.realizarAviso();
        espera();
    }

    private void realizarViaje(){
        //Simula el tiempo de viaje
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void espera(){
        //Simula la espera hasta que vuelve a dormir
        System.out.println("Taxista espera");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Taxista vuelve a dormir");
    }
}
