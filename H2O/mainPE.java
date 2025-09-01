public class mainPE {
    public static void main(String[] args) {
        Laboratorio laboratorio = new Laboratorio(3); // Recipiente para 3 moléculas de agua
        
        // Crear 6 átomos de hidrógeno y 3 de oxígeno (para 3 moléculas de H2O)
        for (int i = 0; i < 6; i++) {
            new Hidrogeno(laboratorio).start();
        }
        
        for (int i = 0; i < 3; i++) {
            new Oxigeno(laboratorio).start();
        }
    }
}