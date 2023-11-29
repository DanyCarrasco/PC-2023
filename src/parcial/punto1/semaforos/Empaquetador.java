package parcial.punto1.semaforos;

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
