package tp03.punto6;

public class Sumador implements Runnable{
    private Sumatoria sumatoria;
    private int[] valores;
    private int cantSumar;
    private int inicio;

    public Sumador(Sumatoria sumatoria, int[] valores, int cant, int inicio){
        this.sumatoria = sumatoria;
        this.valores = valores;
        this.cantSumar = cant;
        this.inicio = inicio;
    }

    public void run(){
        int maximo = cantSumar + inicio;
        int numSumado = 0;
        while (inicio <= maximo){
            numSumado = numSumado + valores[inicio];
            System.out.println(Thread.currentThread().getName()+" suma: "+numSumado);
            inicio++;
        }
        System.out.println(Thread.currentThread().getName()+" suma final: "+numSumado);
        this.sumatoria.sumar(numSumado);
        System.out.println(Thread.currentThread().getName()+" termino");
    }
}
