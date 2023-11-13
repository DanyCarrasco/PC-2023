package tp08.punto4.monitores;

public class Main4Monitores {
    public static void main(String[] args) {
        int cantPersonas = 10;
        HemoterapiaMonitores centro = new HemoterapiaMonitores();
        Persona[] personas = new Persona[cantPersonas];
        Thread[] hilos = new Thread[cantPersonas];
        for (int i = 0; i < cantPersonas; i++) {
            personas[i] = new Persona(centro);
            hilos[i] = new Thread(personas[i], "Persona "+i);
            hilos[i].start();
        }
    }
}
