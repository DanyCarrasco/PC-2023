package tp05.punto3;

public class Main3 {
    public static void main(String[] args) {
        int cantPerros = 3, cantGatos = 3, cantPlatos = 2;
    Comedor2 platos = new Comedor2(cantPlatos);
    Perro[] perros = new Perro[cantPerros];
    Gato[] gatos = new Gato[cantGatos];
    Thread[] hilosPerros = new Thread[cantPerros];
    Thread[] hilosGatos = new Thread[cantGatos];

    for (int i = 0; i < cantPerros; i++) {
        perros[i] = new Perro(platos);
        hilosPerros[i] = new Thread(perros[i],  "Perro "+i);
        hilosPerros[i].start();
    }

    for (int i = 0; i < cantGatos; i++) {
        gatos[i] = new Gato(platos);
        hilosGatos[i] = new Thread(gatos[i],  "Gato "+i);
        hilosGatos[i].start();
    }
}
}
