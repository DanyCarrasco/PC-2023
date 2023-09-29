package actObligatoria;

public class ControlTren implements Runnable{
    private Tren tren;

    public ControlTren(Tren tren){
        this.tren = tren;
    }

    @Override
    public void run() {
        while (true){
            try {
                tren.esperarPasajeros();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hacerRecorrido();
            try {
                tren.liberarPasajeros();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void hacerRecorrido(){
        System.out.println("Empieza el recorrido");
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Termina el recorrido");
    }
}
