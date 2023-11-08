package tp08.punto5.monitores;

public class OllaMonitores {
    private int raciones, cantRaciones;

    public OllaMonitores(int numRaciones){
        raciones = numRaciones;
        cantRaciones= numRaciones;
    }

    public synchronized void empiezaAComer(){
        while(cantRaciones == 0){
            try {
                System.out.println(Thread.currentThread().getName()+" SIGUE ESPERANDO POR OLLA LLENA");
                this.notifyAll();
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        cantRaciones--;
    }

    public synchronized void empiezaACocinar(){
        while(cantRaciones > 0){
            try {
                System.out.println(Thread.currentThread().getName()+" SIGUE DURMIENDO...");
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void terminoDeCocinar(){
        cantRaciones = raciones;
        this.notifyAll();
    }

}
