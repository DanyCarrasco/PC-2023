package parcial.punto1.semaforos;

public class Main1 {
    public static void main(String[] args) {
        int cantEmbotelladoresA=5, cantEmbotelladoresV=5, i=0;
        PlantaEmbotelladora planta = new PlantaEmbotelladora();
        Embotellador[] embA = new Embotellador[cantEmbotelladoresA];
        Embotellador[] embV = new Embotellador[cantEmbotelladoresV];
        Transportador t = new Transportador(planta);
        Empaquetador em = new Empaquetador(planta);
        for (int k = 0; k < cantEmbotelladoresA; k++) {
            embA[k] = new Embotellador(planta, true);
            embA[k].setName("Embotellador-"+i);
            embA[k].start();
            i++;
        }
        for (int j = 0; j < cantEmbotelladoresV; j++) {
            embV[j] = new Embotellador(planta, false);
            embV[j].setName("Embotellador-"+i);
            embV[j].start();
            i++;
        }
        t.start();
        em.start();
    }
}
