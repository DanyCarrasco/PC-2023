package parcial.punto1.locks;

import parcial.punto1.semaforos.PlantaEmbotelladora;

public class Empaquetador extends Thread{
    private PlantaEmbotelladora planta;
    public Empaquetador(PlantaEmbotelladora p){
        planta = p;
    }

    public void run(){
        while(true){
            planta.guardarCaja();
            planta.reponerCaja();
        }
    }
}
