package actObligatoria;

public class Pasajero implements Runnable{
    private Tren tren;
    private boolean viajo;
    public Pasajero(Tren tren){
        this.tren = tren;
        this.viajo = false;
    }

    @Override
    public void run() {
        while (!viajo) {
            System.out.println(Thread.currentThread().getName()+ " sube al tren");
            if (tren.hayLugar()) {
                try {
                    tren.ingresarATren();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                subir();
                System.out.println(Thread.currentThread().getName()+ " deja al tren");
            } else {
                //espera
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //si hay lugar
            //subir al tren y cantPasajeros suma
            //sino hay lugar
            //esperar por un tiempo y despues vuelve a preguntar
        }
    }

    //metodo para subir al tren
    public void subir(){
        //subir al tren
        try{
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.viajo = true;
    }


}
