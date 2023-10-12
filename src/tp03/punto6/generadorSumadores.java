package tp03.punto6;

public class GeneradorSumadores {
    private Sumador[] sumador;
    private int numHilos;
    private int[] arreglo;
    private Sumatoria sum;

    public GeneradorSumadores(int numHilos, int[] arreglo, Sumatoria sum){
        this.numHilos = numHilos;
        this.arreglo = arreglo;
        this.sumador = new Sumador[numHilos];
        this.sum = sum;
    }

    public void crearSumadores(){
        int cantTrabajo, numResto;
        cantTrabajo = arreglo.length / numHilos;
        numResto = arreglo.length % numHilos;
        for (int i = 0; i < sumador.length; i++) {
            if(numResto != 0 && i == sumador.length-1){
                sumador[i] = new Sumador(sum, arreglo, cantTrabajo+numResto, i*cantTrabajo);
            } else {
                sumador[i] = new Sumador(sum, arreglo, cantTrabajo, i*cantTrabajo);
            }
        }
    }

    public Sumador[] getArregloSumador() {
        return this.sumador;
    }
}
