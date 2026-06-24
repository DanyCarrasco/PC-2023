package tpObligatorio;

public class Terminal {
    private String id;
    public FreeShop tienda;
    public SalaEmbarque sala;
    private int puestoEmbarqueInicial, puestoEmbarqueFinal;

    public Terminal(String id, int cantPasajeros, int cantMaxima, int puestoEmbarqueInicial, int puestoEmbarqueFinal){
        this.puestoEmbarqueInicial = puestoEmbarqueInicial;
        this.puestoEmbarqueFinal = puestoEmbarqueFinal;
        sala = new SalaEmbarque(cantPasajeros);
        tienda = new FreeShop(id, cantMaxima);
    }


    
}
