package tpObligatorio;

public class Terminal {
    private String id;
    public FreeShop tienda;
    public SalaEmbarque sala;

    public Terminal(String id, int cantMaxima) {
        this.id = id;
        sala = new SalaEmbarque(id);
        tienda = new FreeShop(id, cantMaxima);
    }

    public String getId() {
        return id;
    }

    public String asignarVuelo() {
        return sala.asignarVuelo();
    }
}
