package tp02;

// del tp 2 del ejercicio 7 inciso a

public class Cajero {
    private String nombre;

    // Constructor y metodos de acceso

    public Cajero(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void esperarXsegundos(int producto){
        try {
            Thread.sleep((producto + (producto - 1))*1000);
        } catch (InterruptedException e) {
            System.out.println(this.nombre + " Interrumpido.");
        }
    }

    public void procesarCompra(Cliente2 cliente, long timeStamp){
        System.out.println("El cajero "+ this.nombre +" COMIENZA A PROCESAR LA COMPRA DEL CLIENTE "+ cliente.getNombre() + " EN EL TIEMPO "+ (System.currentTimeMillis() - timeStamp) / 1000 + "seg");

        for (int i = 0; i < cliente.getCarroCompra().length; i++) {
            this.esperarXsegundos(cliente.getCarroCompra()[i]);
            System.out.println("Procesando el producto "+ (i + 1)+ "->Tiempo: "+ (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
        }
        System.out.println("El cajero "+ this.nombre +" HA TERMINADO DE PROCESAR "+ cliente.getNombre()+" EN EL TIEMPO: "+ (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
    }
}
