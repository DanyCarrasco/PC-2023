package tpObligatorio;

import java.util.concurrent.locks.ReentrantLock;

public class PuestoInformes {
    private final PuestoAtencion[] puestos;
    private final ReentrantLock lock = new ReentrantLock(true);

    public PuestoInformes(PuestoAtencion[] puestos) {
        this.puestos = puestos;
    }

    public PuestoAtencion derivarAPuesto() {
        lock.lock();
        try {
            int indice = (int) (Math.random() * puestos.length);
            System.out.println(Thread.currentThread().getName()
                    + " es derivado al puesto de atencion de " + puestos[indice].getNombre());
            return puestos[indice];
        } finally {
            lock.unlock();
        }
    }
}
