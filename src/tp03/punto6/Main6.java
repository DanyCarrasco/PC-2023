package tp03.punto6;

import java.util.Random;

public class Main6 {
    public static void main(String[] args) {
        int numHilos = 3, numArreglo = 10;
        int[] arregloInt = new int[numArreglo];
        Sumatoria sum = new Sumatoria();
        iniciarArreglo(arregloInt);
        GeneradorSumadores generador = new GeneradorSumadores(numHilos, arregloInt, sum);
	generador.crearSumadores();
        Thread[] hilos = new Thread[numHilos];
        crearHilos(hilos, generador.getArregloSumador());
        iniciarHilos(hilos);
        esperarHilos(hilos);
        System.out.println("La suma total final es: "+sum.getTotal());
    }

    public static void iniciarArreglo(int[] arreglo) {
        int num = 0;
        Random rand = new Random();
        for (int i = 0; i < arreglo.length; i++) {
            num = (rand.nextInt(10) + 1);
            System.out.println("Agrega "+ num);
            arreglo[i] = num;
        }
    }

    public static void crearHilos(Thread[] hilos, Sumador[] arregloSumador){

        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(arregloSumador[i], "Thread "+i);
        }
    }

    public static void iniciarHilos(Thread[] hilos) {
        for (int i = 0; i < hilos.length; i++) {
            hilos[i].start();
        }
    }

    public static void esperarHilos(Thread[] hilos) {
        for (int i = 0; i < hilos.length; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
