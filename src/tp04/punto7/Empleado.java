package punto7;

public class Empleado implements Runnable{
    private int opcionMenu;
    private Confiteria silla;

    public Empleado(Confiteria silla){
        this.silla = silla;
        this.opcionMenu = (int) (Math.random()*3 + 1); 
    }

    public void run(){
        System.out.println(Thread.currentThread().getName()+ " se acerca y ve si hay lugar");
        silla.solicitarSilla();
        elegirPedido(opcionMenu);
        silla.realizarPedido();
        System.out.println(Thread.currentThread().getName()+ " espera que le sirvan");
        silla.esperarComida();
        System.out.println(Thread.currentThread().getName() + " recibe la comida y empieza a comer");
        comer();
        System.out.println(Thread.currentThread().getName()+" termina de comer, deja la silla, agradece al mozo y vuelve al trabajo");
        silla.dejarSilla();
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

    private void comer(){
        //Simula el tiempo que tarda en comer
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
