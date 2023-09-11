package tp03;

public class Main2 {
    public static void main(String[] args) {
        Energia energia = new Energia();
        Sanador sanador = new Sanador(energia);
        CriaturaOscura criaturaOscura = new CriaturaOscura(energia);
        Thread sanadorThread = new Thread(sanador, "Sanador");
        Thread criaturaOscuraThread = new Thread(criaturaOscura, "Criatura Oscura");
        sanadorThread.start();
        criaturaOscuraThread.start();
        //try {
       //     sanadorThread.join();
        //    criaturaOscuraThread.join();
        //} catch (InterruptedException e) {
        //    throw new RuntimeException(e);
        //}
        }
}
