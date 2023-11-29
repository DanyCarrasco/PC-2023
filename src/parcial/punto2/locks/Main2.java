package parcial.punto2.locks;

public class Main2 {
    public static void main(String[] args) {
        int cantHacerAgua = 5, cantVeces = 2;
        Laboratorio lab = new Laboratorio(cantHacerAgua);
        generadorDeAtomos(lab, (cantHacerAgua * cantVeces));
    }

    private static void generadorDeAtomos(Laboratorio lab, int cantHacerAgua) {
        for (int i = 0; i < 10; i++) {
            new Hidrogeno(lab).start();
            new Hidrogeno(lab).start();
            new Oxigeno(lab).start();
        }
    }
}
