package tp05.punto2;

public class Empleado implements Runnable{
    private Confiteria sillas;
    private int opcionMenu;

    public Empleado(Confiteria sillas){
        this.sillas = sillas;
        this.opcionMenu = (int) (Math.random()*3 + 1);
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+ " quiere ingresar y tomar una silla");
        sillas.tomarAsiento();
        System.out.println(Thread.currentThread().getName() + " entra y se sienta");
        elegirPedido(opcionMenu);
        sillas.pedirAMozo();
        System.out.println(Thread.currentThread().getName()+ " espera su pedido del mozo");
        sillas.esperaPedidoMozo();
        System.out.println(Thread.currentThread().getName()+ " recibe su pedido del mozo");
        if (opcionMenu == 3) {
            System.out.println(Thread.currentThread().getName()+" pide al cocinero su comida");
            sillas.pedirACocinero();
            System.out.println(Thread.currentThread().getName()+ " espera su comida del cocinero");
            sillas.esperaPedidoCocinero();
            System.out.println(Thread.currentThread().getName()+ " recibe su pedido del cocinero");
        }
        beberComer();
        System.out.println(Thread.currentThread().getName()+" termina de comer, deja la silla, agradece y vuelve al trabajo");
        sillas.dejarAsiento();
    }

    private void elegirPedido(int opcion){
        //Simula el tiempo de elegir una comida en el menu
        switch (opcion) {
            case 1:
                System.out.println(Thread.currentThread().getName()+ " eligio 1 cafe");
                break;
                case 2:
                System.out.println(Thread.currentThread().getName()+ " eligio 1 porcion de pollo frito");
                break;
                case 3:
                System.out.println(Thread.currentThread().getName()+" eligio 1 cafe y una porcion de pollo");
                break;
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void beberComer(){
        //Simula el tiempo que tarda en beber y/o comer
        switch (opcionMenu) {
            case 1:
                System.out.println(Thread.currentThread().getName()+ " toma 1 cafe");
                break;
                case 2:
                System.out.println(Thread.currentThread().getName()+ " come 1 porcion de pollo frito");
                break;
                case 3:
                System.out.println(Thread.currentThread().getName()+" toma 1 cafe y come una porcion de pollo");
                break;
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
