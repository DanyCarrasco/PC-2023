package tp03.punto4;

public class Main4 {
    public static void main(String[] args) {
        Area area = new Area("Area 1",10);

        Thread Visitantes1 = new Thread(new Visitantes(area, 5));
        Thread Visitantes2 = new Thread(new Visitantes(area, 3));
        Thread Visitantes3 = new Thread(new Visitantes(area, 4));

        Visitantes1.start();
        Visitantes2.start();
        Visitantes3.start();
    }
}
