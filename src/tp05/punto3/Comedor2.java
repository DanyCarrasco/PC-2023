package tp05.punto3;

import java.util.concurrent.Semaphore;

public class Comedor2 {
    private Semaphore semGatos = new Semaphore(0);
    private Semaphore semPerros = new Semaphore(0);
    private Semaphore semPlatos;
    private Semaphore mutex = new Semaphore(1);
    private boolean primerTurno = true;
    int contador = 0;
    int cantMaxima, perrosEsperando, gatosEsperando, perrosComiendo, gatosComiendo;

    public Comedor2(int cantidad) {
        semPlatos = new Semaphore(cantidad);
        this.cantMaxima = cantidad * 2;
        this.perrosEsperando = 0;
        this.gatosEsperando = 0;
        this.perrosComiendo = 0;
        this.gatosEsperando = 0;
        this.gatosComiendo = 0;
    }

    public void entraPerro() {
        try {
            this.mutex.acquire();
            if (primerTurno) {
                this.semPerros.release(cantMaxima);
            }
            this.primerTurno = false;
            this.mutex.release();
        } catch (InterruptedException e) {
            // TODO: handle exception
        }

        try {
            this.semPerros.acquire();
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
        System.out.println(Thread.currentThread().getName() + " esta preparado para entrar");
        try {
            this.semPlatos.acquire();
            this.mutex.acquire();
            this.perrosComiendo++;
            this.mutex.release();
        } catch (InterruptedException var3) {
        }

        System.out.println("Entra " + Thread.currentThread().getName());
    }

    public void entraGato() {
        try {
            this.mutex.acquire();
            if (primerTurno) {
                this.semGatos.release(cantMaxima);
            }
            this.primerTurno = false;
            this.mutex.release();
        } catch (InterruptedException e) {
            // TODO: handle exception
        }

        try {
            this.semGatos.acquire();
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println(Thread.currentThread().getName() + " esta preparado para entrar");
        try {
            this.semPlatos.acquire();
            this.mutex.acquire();
            this.gatosComiendo++;
            this.mutex.release();
        } catch (InterruptedException var3) {
        }

        System.out.println("Entra " + Thread.currentThread().getName());
    }

    public void salePerro() {
        System.out.println("Sale " + Thread.currentThread().getName());
        try {
            this.mutex.acquire();
            this.perrosComiendo--;
            this.mutex.release();
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
        this.semPlatos.release();
        try {
            this.mutex.acquire();
            this.contador++;
            if (this.contador == cantMaxima) {
                this.semGatos.release(cantMaxima);
                this.contador = 0;
            } else {
                if (primerTurno) {
                    
                }
            }
            this.mutex.release();
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }

    public void saleGato() {
        System.out.println("Sale " + Thread.currentThread().getName());
        try {
            this.mutex.acquire();
            this.gatosComiendo--;
            this.mutex.release();
        } catch (Exception e) {
            // TODO: handle exception
        }
        this.semPlatos.release();
        try {
            this.mutex.acquire();
            this.contador++;
            if (this.contador == cantMaxima) {
                this.semPerros.release(cantMaxima);
                this.contador = 0;
            }
            this.mutex.release();
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }
}
