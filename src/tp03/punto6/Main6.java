package tp03.punto6;

import java.util.Random;

public class Main6 {
    public static void main(String[] args) {
        int numHilos = 3, numArreglo = 10;
        int[] arregloInt = new int[numArreglo];
        Sumador[] sumadores = new Sumador[numHilos];
        iniciarArreglo(arregloInt);

    }

    private static int numeroDeTrabajo(int numHilos, int numArreglo){
        int resto = numArreglo % numHilos;
        int numTrabajo = 0;
        if(resto == 0){
            numTrabajo = numArreglo / numHilos;
        } else {
            numTrabajo = (numArreglo / numHilos) + resto;
        }
    }

    private static void iniciarArreglo(int[] arreglo){
        Random rand = new Random();
        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] = (rand.nextInt() * 10);
        }
    }

    private static void crearSumadores(Sumador[] sumadores){
        for (int i = 0; i < sumadores.length; i++) {
            sumadores[i] = new Sumador(new Sumatoria(), arregloInt, 10, i*10);
        }
    }
}
