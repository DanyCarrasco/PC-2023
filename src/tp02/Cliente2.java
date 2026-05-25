package tp02;

// del tp 2 del ejercicio 7 inciso a

public class Cliente2 {
    private String nombre;
    private int[] carroCompra;

    public Cliente2(String nombre, int[] carroCompra){
        this.nombre = nombre;
        this.carroCompra = carroCompra;
    }

    public String getNombre(){
        return this.nombre;
    }

    public int[] getCarroCompra(){
        return this.carroCompra;
    }
}
