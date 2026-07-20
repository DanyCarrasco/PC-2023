package tpObligatorio;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PuestoInformes {
    private final PuestoAtencion[] puestos;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition esperandoTurno = lock.newCondition();
    private final Condition empleadoEspera = lock.newCondition();
    private final Condition procesado = lock.newCondition();
    // ocupado: true mientras un pasajero esta en fase de procesamiento (desde que entra
    // hasta que sale completamente). Evita que un pasajero nuevo se cuelen en la cola
    // de procesado antes de que el anterior haya salido.
    private boolean ocupado = false;
    // atendiendo: true cuando el employee esta procesando activamente a un pasajero
    private boolean atendiendo = false;
    private PuestoAtencion puestoAsignado;

    public PuestoInformes(PuestoAtencion[] puestos) {
        this.puestos = puestos;
    }

    public PuestoAtencion llegarAInforme() {
        lock.lock();
        try {
            while (ocupado) {
                System.out.println(Thread.currentThread().getName()
                        + " espera para ser atendido por empleado del Puesto de Informe");
                esperandoTurno.await();
            }
            ocupado = true;
            atendiendo = true;
            empleadoEspera.signal();
            while (atendiendo) {
                procesado.await();
            }
            // Ya tengo mi resultado, libero para que el siguiente pueda entrar
            ocupado = false;
            esperandoTurno.signal();
            System.out.println(Thread.currentThread().getName()
                    + " es derivado al puesto de atencion de " + puestoAsignado.getNombre());
            return puestoAsignado;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            if (ocupado) {
                ocupado = false;
                atendiendo = false;
                esperandoTurno.signal();
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

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
            procesado.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
