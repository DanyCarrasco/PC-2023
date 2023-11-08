package tp07.punto2;

public class Main2 {
    public static void main(String[] args) {
        int limiteComputadoras = 5, limiteLibros = 3, cantProgramadores = 7;
        Recurso recursos = new Recurso(limiteComputadoras, limiteLibros);
        Programador[] programadores = new Programador[cantProgramadores];
        Thread[] hilos = new Thread[cantProgramadores];
        for (int i = 0; i < cantProgramadores; i++) {
            programadores[i] = new Programador(recursos);
            hilos[i] = new Thread(programadores[i], "Programador "+i);
            hilos[i].start();
        }
    }
}
