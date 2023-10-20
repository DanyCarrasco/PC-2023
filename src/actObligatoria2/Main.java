package actObligatoria2;

public class Main {
    public static void main(String[] args) {
        int cantGato = 22, cantPerro = 22, capacidad = 20;
        int total = cantPerro + cantGato;
        Comedor comedor = new Comedor(capacidad);
        Animal[] animal = new Animal[total];
        Thread[] animales = new Thread[total];
        for (int i = 0; i < total; i++) {
            if (i < cantGato) {
                animal[i] = new Animal("Gato", "" + i, comedor);
            } else {
                animal[i] = new Animal("Perro", "" + i, comedor);
            }
        }

        for (int i = 0; i < total; i++) {
            if (i < cantGato) {
                animales[i] = new Thread(animal[i]);
            } else {
                animales[i] = new Thread(animal[i]);
            }
            animales[i].start();
        }
    }
}
