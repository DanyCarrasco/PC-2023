package LaExposicion;

public class Sala {
    private int cantidad;
    private int esperandoCriticos, cantVisitantes;
    private boolean responsableSala, critico;

    public Sala(int cantidad){
        this.cantidad = cantidad;
        this.esperandoCriticos = 0;
        this.cantVisitantes = 0;
        this.responsableSala = false;
        this.critico = false;
    }

    public synchronized void entraVisitante(){
        while (critico || esperandoCriticos > 0 || cantVisitantes == cantidad) {
            System.out.println(Thread.currentThread().getName()+ " SIGUE ESPERANDO PARA ENTRAR A LA SALA ...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        this.cantVisitantes++;
    }

    public synchronized void saleVisitante(){
        this.cantVisitantes--;
        this.notifyAll();
    }
    

    public synchronized void entraResponsable(){
        while (critico || esperandoCriticos > 0 || responsableSala) {
            System.out.println(Thread.currentThread().getName()+ " SIGUE ESPERANDO PARA ENTRAR A LA SALA ...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        this.responsableSala = true;
    }

    public synchronized void saleResponsable(){
        this.responsableSala = false;
        this.notifyAll();
    }

    public synchronized void entraCritico(){
        this.esperandoCriticos++;
        while (critico || cantVisitantes > 0 || responsableSala) {
            System.out.println(Thread.currentThread().getName()+ " SIGUE ESPERANDO PARA ENTRAR A LA SALA ...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        this.esperandoCriticos--;
        critico = true;
    }

    public synchronized void saleCritico(){
        this.critico = false;
        this.notifyAll();
    }
}
