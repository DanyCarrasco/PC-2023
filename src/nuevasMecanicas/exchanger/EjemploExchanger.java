package nuevasMecanicas.exchanger;

import java.util.concurrent.Exchanger;
public class EjemploExchanger {
    public static void main(String[] args) {
        Exchanger<String> ex = new Exchanger<>();

        // Hilo A
        new Thread(() -> {
            try {
                String datosA = "Mensaje Secreto de A";
                System.out.println("Hilo A listo para enviar sus datos...");
                
                // Entrega sus datos y recibe los de B
                String respuestaDeB = ex.exchange(datosA);
                
                System.out.println("Hilo A recibió: " + respuestaDeB);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        // Hilo B
        new Thread(() -> {
            try {
                // Simulamos un retraso en el Hilo B
                Thread.sleep(2000);
                String datosB = "Mensaje Secreto de B";
                System.out.println("Hilo B llegó al punto de intercambio.");
                
                // Entrega sus datos y recibe los de A
                String respuestaDeA = ex.exchange(datosB);
                
                System.out.println("Hilo B recibió: " + respuestaDeA);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
