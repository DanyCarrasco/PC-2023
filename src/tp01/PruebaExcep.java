package tp01;

import java.util.Scanner;

public class PruebaExcep {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            metodoA();
        }catch(ArithmeticException e){
            System.out.println("Excepcion en metodoA()");
            System.out.println(e.getMessage());
        }
        try {
            metodoB();
        }catch(ArithmeticException e){
            System.out.println("Excepcion en metodoB()");
            System.out.println(e.getMessage());
        }
        metodoC();
    }

    private static void metodoA() throws ArithmeticException {
        /*Escriba un método que ingrese la edad de una persona y
        dispare una excepción si la persona es menor de edad.*/
        int edad;
        System.out.println("Ingrese su edad:");
        edad = sc.nextInt();
        if (edad < 18) {
            throw new ArithmeticException("Es menor de 18 años");
        }
    }

    private static void metodoB() throws ArithmeticException{
        /*Escriba un método que ingrese un numero de la ruleta y
        dispare una excepción cuando al jugar no salga dicho número.*/
        System.out.println("Ingrese un numero del 0 al 99");
        int num = sc.nextInt();
        int numRuleta = (int) (Math.random() * 100);
        if (numRuleta == num){
            System.out.println("Ganaste!");
        } else {
            throw new ArithmeticException("Perdiste!");
        }
    }

    private static void metodoC() {
        /*Escriba un método en el que se pida ingresar 5 números a una colección y
        al mostrarlos, trate de mostrar 7 valores de la misma, generando una excepción.*/
        int[] arrayInt = new int[6];
        int i = 0;
        while (i < 6) {
            System.out.println("Ingrese un numero:");
            arrayInt[i] = sc.nextInt();
            i++;
        }
        try {
            for (int j = 0; j < 7; j++) {
                System.out.println("Valor en posicion " + j + ": " + arrayInt[j]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Excepcion en metodoC()");
            e.printStackTrace();
        }
    }

}
