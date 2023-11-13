package tp08.punto2.monitores;

public class SalaMonitores {
    private int capacidad, cantVisitantes, cantMantenimiento, investigadoresEsperando, cantInvestigadores;
    boolean personaSillaDeRuedas;

    public SalaMonitores() {
        this.capacidad = 50;
        cantVisitantes = 0;
        investigadoresEsperando = 0;
        cantMantenimiento = 0;
        personaSillaDeRuedas = false;
        cantInvestigadores = 0;
    }

    public synchronized void ingresaVisitante(boolean tieneSilla) {
        while (investigadoresEsperando > 0 || cantVisitantes >= capacidad || cantInvestigadores > 0) {
            System.out.println(Thread.currentThread().getName() + " SIGUE ESPERANDO...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (tieneSilla) {
            capacidad = 5;
            System.out.println("se cambio la capacidad 10 ---> 5");
        }
        cantVisitantes++;
    }

    public synchronized void saleVisitante(boolean tieneSilla) {
        cantVisitantes--;
        if (tieneSilla) {
            capacidad = 10;
            System.out.println("se cambio la capacidad 5 ---> 10");
        }
        this.notifyAll();
    }

    public synchronized void ingresaInvestigador() {
        investigadoresEsperando++;
        while (cantVisitantes > 0 || cantMantenimiento > 0 || cantInvestigadores > 0) {
            System.out.println(Thread.currentThread().getName() + " SIGUE ESPERANDO...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        investigadoresEsperando--;
        cantInvestigadores++;
    }

    public synchronized void saleInvestigador() {
        cantInvestigadores--;
        this.notifyAll();
    }

    public synchronized void ingresaMantenimiento() {
        while (cantVisitantes > 0 || cantInvestigadores > 0 || investigadoresEsperando > 0) {
            System.out.println(Thread.currentThread().getName() + " SIGUE ESPERANDO...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        cantMantenimiento++;
    }

    public synchronized void saleMantenimiento() {
        cantMantenimiento--;
        this.notifyAll();
    }
}
