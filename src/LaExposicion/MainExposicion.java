package LaExposicion;

public class MainExposicion {
    public static void main(String[] args) {
        int cantVisitantes = 8, cantCriticos = 3, cantResponsables = 3;
        Sala exposicion = new Sala(5);
        Visitantes[] visitantes = new Visitantes[cantVisitantes];
        Critico[] criticos = new Critico[cantCriticos];
        ResponsableSala[] responsables = new ResponsableSala[cantResponsables];
        Thread[] hilosVisitantes = new Thread[cantVisitantes];
        for (int i = 0; i < cantVisitantes; i++) {
            visitantes[i] = new Visitantes(exposicion);
            hilosVisitantes[i] = new Thread(visitantes[i], "Visitante " + i);
            hilosVisitantes[i].start();
        }
        Thread[] hilosCriticos = new Thread[cantCriticos];
        for (int i = 0; i < cantCriticos; i++) {
            criticos[i] = new Critico(exposicion);
            hilosCriticos[i] = new Thread(criticos[i], "Criticos " + i);
            hilosCriticos[i].start();
        }
        Thread[] hilosResponsables = new Thread[cantResponsables];
        for (int i = 0; i < cantCriticos; i++) {
            responsables[i] = new ResponsableSala(exposicion);
            hilosResponsables[i] = new Thread(responsables[i], "ResponsableSala " + i);
            hilosResponsables[i].start();
        }
    }
}
