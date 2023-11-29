package parcial.punto1.locks;

import parcial.punto1.semaforos.PlantaEmbotelladora;

public class Transportador extends Thread{
    private PlantaEmbotelladora planta;
    public Transportador(PlantaEmbotelladora p){
        planta = p;
    }

    public void run(){
        while(true){
            planta.repartirCajas();
            System.out.println("El almacen esta vacio");
            System.out.println("Transportador repartio cajas y libera espacio en almacen");
        }
    }
}
