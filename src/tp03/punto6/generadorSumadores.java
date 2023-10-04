package tp03.punto6;

public class generadorSumadores {
    private Sumador[] sumador;
    private int numHilos, cantTrabajo;
    private int[] arreglo;

    public generadorSumadores(int numHilos, int[] arreglo){
        this.numHilos = numHilos;
        this.arreglo = arreglo;
        this.sumador = new Sumador[numHilos];
    }

    public void crearSumadores(){
        for (int i = 0; i < sumador.length; i++) {
            sumador[i] = new Sumador(new Sumatoria(), arreglo, 10, i*10);
        }
    }
}
