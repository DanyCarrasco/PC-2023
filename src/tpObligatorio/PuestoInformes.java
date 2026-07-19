package tpObligatorio;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PuestoInformes {
    private final PuestoAtencion[] puestos;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition pasajeroEspera = lock.newCondition();
    private final Condition empleadoEspera = lock.newCondition();
    private boolean atendiendo = false;
    private PuestoAtencion puestoAsignado;

    public PuestoInformes(PuestoAtencion[] puestos) {
        this.puestos = puestos;
    }

    // Pasajero llega al puesto de informes y espera a ser atendido por un empleado del puesto de informes
    public PuestoAtencion llegarAInforme() {
        lock.lock();
        try {
            while (atendiendo) {
                System.out.println(Thread.currentThread().getName()
                        + " espera para ser atendido por empleado del Puesto de Informe");
                pasajeroEspera.await();
            }
            atendiendo = true;
            empleadoEspera.signal();
            while (atendiendo) {
                pasajeroEspera.await();
            }
            System.out.println(Thread.currentThread().getName()
                    + " es derivado al puesto de atencion de " + puestoAsignado.getNombre());
            return puestoAsignado;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }

    // Metodo que atiende al pasajero y lo deriva a un puesto de atencion
    public void atenderPasajero() {
        lock.lock();
        try {
            while (!atendiendo) {
                empleadoEspera.await();
            }
            int indice = (int) (Math.random() * puestos.length);
            puestoAsignado = puestos[indice];
            System.out.println(Thread.currentThread().getName()
                    + " atiende al pasajero y lo deriva a un puesto de atencion");
            atendiendo = false;
            pasajeroEspera.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
