package actObligatoria2;

public class TestComedorAnimales {
    /*public static void main(String[] args) {
        int capacidad = 5; // Cambia la capacidad según tus necesidades
        ComedorAnimales comedor = new ComedorAnimales(capacidad);

        Thread perro1 = new Thread(() -> {
            try {
                comedor.comerPerro();
                System.out.println("Perro 1 está comiendo.");
                Thread.sleep(1000); // Simula que el perro está comiendo durante un tiempo
                comedor.comerPerro(); // Intenta comer de nuevo
                System.out.println("Perro 1 ha terminado de comer.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread gato1 = new Thread(() -> {
            try {
                comedor.comerGato();
                System.out.println("Gato 1 está comiendo.");
                Thread.sleep(1000); // Simula que el gato está comiendo durante un tiempo
                comedor.comerGato(); // Intenta comer de nuevo
                System.out.println("Gato 1 ha terminado de comer.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread gato2 = new Thread(() -> {
            try {
                comedor.comerGato();
                System.out.println("Gato 2 está comiendo.");
                Thread.sleep(1000); // Simula que el gato está comiendo durante un tiempo
                comedor.comerGato(); // Intenta comer de nuevo
                System.out.println("Gato 2 ha terminado de comer.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        perro1.start();
        gato1.start();
        gato2.start();
    }*/
    public static void main(String[] args) {
        int capacidad = 2; // Cambiar la capacidad según la cantidad deseada de platos

        ComedorAnimales comedor = new ComedorAnimales(capacidad);

        // Crear perros y gatos para probar
        int numPerros = 3;
        int numGatos = 3;

        for (int i = 0; i < numPerros; i++) {
            Thread perro = new Thread(() -> {
                try {
                    System.out.println("Perro quiere comer");
                    comedor.comerPerro();
                    System.out.println("Perro comiendo");
                    Thread.sleep(5000);
                    System.out.println("Perro termino de comer");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            perro.start();
        }

        for (int i = 0; i < numGatos; i++) {
            Thread gato = new Thread(() -> {
                try {
                    System.out.println("Gato quiere comer");
                    comedor.comerGato();
                    System.out.println("Gato comiendo");
                    Thread.sleep(5000);
                    System.out.println("Gato termino de comer");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            gato.start();
        }
    }
}

