package tpObligatorio;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PuestoInformes {
    private final PuestoAtencion[] puestos;
    private final ReentrantLock lock = new ReentrantLock(true);
    private Condition esperar = lock.newCondition();
    private Condition informar = lock.newCondition();

    private int pasajerosEsperando = 0;
    private boolean atendiendo = false;

    public PuestoInformes(PuestoAtencion[] puestos) {
        this.puestos = puestos;
    }

    // lo realiza un pasajero
    public PuestoAtencion llegarAInforme() {
        PuestoAtencion puesto;
        lock.lock();
        pasajerosEsperando++;
        while (atendiendo) {
            System.out.println(
                    Thread.currentThread().getName() + " espera para ser atendido por empleado del Puesto de Informe");
            try {
                esperar.await();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " es atendido por empleado de Puesto de Informe");
        pasajerosEsperando--;
        int indice = (int) (Math.random() * puestos.length);
        puesto = puestos[indice];
        System.out.println(Thread.currentThread().getName()
                + " es derivado al puesto de atencion de " + puesto.getNombre());
        atendiendo = false;
        lock.unlock();
        return puesto;
    }

    // lo realiza empleado Informe
    public void atenderPasajero() {
        lock.lock();
        while (pasajerosEsperando <= 0) {
            try {
                informar.await();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        atendiendo = true;
        System.out
                .println(Thread.currentThread().getName() + "atiende al pasajero y lo deriva a un puesto de atencion");
        lock.unlock();
    }

    /*
     * public PuestoAtencion derivarAPuesto() {
     * int indice = 0;
     * lock.lock();
     * try {
     * indice = (int) (Math.random() * puestos.length);
     * System.out.println(Thread.currentThread().getName()
     * + " es derivado al puesto de atencion de " + puestos[indice].getNombre());
     * } finally {
     * lock.unlock();
     * }
     * return puestos[indice];
     * }
     */
}
