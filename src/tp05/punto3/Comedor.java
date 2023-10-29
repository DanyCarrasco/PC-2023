package tp05.punto3;

import java.util.concurrent.Semaphore;

public class Comedor {
    private Semaphore espacios;
    private Semaphore maximoPerros;
    private Semaphore maximoGatos;
    private Semaphore mutex;
    private int platos, perrosComiendo, gatosComiendo, perrosEsperando, gatosEsperando, cantMaxima;

    public Comedor(int cantidad) {
        this.platos = cantidad;
        this.espacios = new Semaphore(cantidad, true);
        this.maximoPerros = new Semaphore(cantidad * 2);
        this.maximoGatos = new Semaphore(cantidad * 2);
        this.mutex = new Semaphore(1);
        this.cantMaxima = 0;
        this.perrosComiendo = 0;
        this.gatosComiendo = 0;
        this.perrosEsperando = 0;
        this.gatosEsperando = 0;
    }

    public void entrar(String tipo) {
        if (tipo.equals("Perro")) {
            entraPerro();
        } else {
            entraGato();
        }
    }

    private void entraPerro() {
        try {
            if (perrosComiendo == 0) {
                // Si es el primer perro, libera los permisos de la "sala de espera" de perros
                this.maximoPerros.release(platos * 2);
            }
            if (cantMaxima < (platos * 2)) {
                //hay lugar en la "sala de espera" de perros
                this.mutex.acquire();
                cantMaxima++;
                this.mutex.release();
                this.maximoPerros.acquire();
            } else {
                //sigue esperando afuera
                perrosEsperando++;
            }

            if (perrosComiendo < platos) {
                //hay lugar en el comedor
                mutex.acquire();
                perrosComiendo++;
                mutex.release();
                this.espacios.acquire();
            }
        } catch (InterruptedException e) {
        }
    }

    private void entraGato() {
        try {
            if (gatosComiendo == 0) {
                // Si es el primer gato, libera los permisos de la "sala de espera" de gatos
                this.maximoGatos.release(platos * 2);
            }

            if (cantMaxima < (platos * 2)) {
                //hay lugar en la "sala de espera" de gatos
                this.mutex.acquire();
                cantMaxima++;
                this.mutex.release();
                this.maximoGatos.acquire();
            } else {
                //sigue esperando afuera
                gatosEsperando++;
            }

            if (gatosComiendo <= platos) {
                //hay lugar en el comedor
                mutex.acquire();
                gatosComiendo++;
                mutex.release();
                this.espacios.acquire();
            }
        } catch (InterruptedException e) {
        }
    }

    public void salir(String tipo) {
        if (tipo.equals("Perro")) {
            salePerro();
        } else {
            saleGato();
        }
    }

    private void salePerro() {
        try {
            this.mutex.acquire();
            this.perrosComiendo--;
            this.mutex.release();

            if (perrosComiendo == 0) {
                // Soy el ultimo perro
                if (cantMaxima == platos * 2) {
                    // Es momento de cambiar el turno para los gatos
                    this.mutex.acquire();
                    cantMaxima = 0;
                    this.mutex.release();
                    this.maximoGatos.release(platos * 2);
                    //al momento de liberar para ingresen gatos, 
                    //tambien tendria que "cerrar" (seria adquirir los platos*2 permisos) para que no entren perros 
                    //en la "sala de espera" de perros??
                } else {
                    if (perrosEsperando > 0) {
                        // Le doy lugar a un perro en la "sala de espera" de perros
                        // Esta bien que libere en este if o debe estar fuera del if para que siempre libere??
                        this.maximoPerros.release();
                    }
                }

            }
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }

    private void saleGato() {
        try {
            this.mutex.acquire();
            this.gatosComiendo--;
            this.mutex.release();

            if (gatosComiendo == 0) {
                // Soy el ultimo gato
                if (cantMaxima == platos * 2) {
                    // Es momento de cambiar el turno para los perros
                    this.mutex.acquire();
                    cantMaxima = 0;
                    this.mutex.release();
                    this.maximoPerros.release(platos * 2);
                    //al momento de liberar para ingresen perros, 
                    //tambien tendria que "cerrar" (seria adquirir los platos*2 permisos) para que no entren gatos 
                    //en la "sala de espera" de gatos??
                } else {
                    if (gatosEsperando > 0) {
                        // Le doy lugar a un gato en la "sala de espera" de gatos
                        // Esta bien que libere en este if o debe estar fuera del if para que siempre libere??
                        this.maximoGatos.release();
                    }
                }

            }
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }
}
