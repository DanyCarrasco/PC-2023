package tp05.punto1;

public class Main1 {
    public static void main(String[] args) {
        int capacidad = 4, cantPersonas = 10;
        GestorPiscina piscina = new GestorPiscina(capacidad);
        Persona[] personas = new Persona[cantPersonas];
        Thread[] hilosPersonas = new Thread[cantPersonas];
        for (int i = 0; i < cantPersonas; i++) {
            personas[i] = new Persona(piscina);
            hilosPersonas[i] = new Thread(personas[i], "Persona "+ i);
            hilosPersonas[i].start();
        }
    }
}
