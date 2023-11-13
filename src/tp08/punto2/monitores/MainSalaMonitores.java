package tp08.punto2.monitores;

public class MainSalaMonitores {
    public static void main(String[] args) {
        int cantVisitantes = 15, cantInvestigadores = 5, cantMantenimiento = 5;
        SalaMonitores sala = new SalaMonitores();
        Visitante[] visitantes = new Visitante[cantVisitantes];
        Investigador[] investigadores = new Investigador[cantInvestigadores];
        Mantenimiento[] mantenimientos = new Mantenimiento[cantMantenimiento];
        Thread[] hilosVisitantes = new Thread[cantVisitantes];
        for (int i = 0; i < cantVisitantes; i++) {
            visitantes[i] = new Visitante(sala);
            hilosVisitantes[i] = new Thread(visitantes[i], "Visitante " + i);
            hilosVisitantes[i].start();
        }
        Thread[] hilosInvestigadores = new Thread[cantInvestigadores];
        for (int i = 0; i < cantInvestigadores; i++) {
            investigadores[i] = new Investigador(sala);
            hilosInvestigadores[i] = new Thread(investigadores[i], "Investigador " + i);
            hilosInvestigadores[i].start();
        }
        Thread[] hilosMantenimiento = new Thread[cantMantenimiento];
        for (int i = 0; i < cantMantenimiento; i++) {
            mantenimientos[i] = new Mantenimiento(sala);
            hilosMantenimiento[i] = new Thread(mantenimientos[i], "Mantenimiento " + i);
            hilosMantenimiento[i].start();
        }
    }
}
