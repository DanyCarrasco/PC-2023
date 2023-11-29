package parcial.punto1.semaforos;

import java.util.concurrent.Semaphore;

public class PlantaEmbotelladora {
    private int cantBotellasAgua;
    private int cantBotellasVino;
    private int cantCajasAlmacen;

    private char etiquetaCaja;

    //Exclusion mutua
    private Semaphore mutexAgua;
    private Semaphore mutexVino;
    private Semaphore mutexAlmacen;
    private Semaphore cajaAgua;
    private Semaphore cajaVino;

    //comunicacion
    private Semaphore cajaLista;
    private Semaphore cajaVacia;
    private Semaphore almacenLleno;
    private Semaphore almacenVacio;

    public PlantaEmbotelladora() {
        cantBotellasAgua = 0;
        cantBotellasVino = 0;
        cantCajasAlmacen = 0;

        cajaAgua = new Semaphore(10, true);
        cajaVino = new Semaphore(10, true);
        mutexAgua = new Semaphore(1, true);
        mutexVino = new Semaphore(1, true);
        mutexAlmacen = new Semaphore(1, true);

        cajaLista = new Semaphore(0);
        cajaVacia = new Semaphore(0);
        almacenLleno = new Semaphore(0);
        almacenVacio = new Semaphore(10);
    }

    public void guardarBotella(boolean botellaAgua) {
        if (botellaAgua) {
            guardarEnCajaA();
        } else {
            guardarEnCajaV();
        }
    }

    private void guardarEnCajaA() {
        try {
            cajaAgua.acquire();

            mutexAgua.acquire();
            cantBotellasAgua++;
            System.out.println(Thread.currentThread().getName()+" guardo una botella de agua en caja, la caja tiene "+cantBotellasAgua);
            if (cantBotellasAgua == 10) {
                etiquetaCaja = 'A';
                System.out.println("Se lleno la caja con botellas de agua");
                cajaLista.release();
                System.out.println("Se espera una caja de agua vacia");
                cajaVacia.acquire();
                System.out.println("Se reemplaza con una caja de vino vacia");
            }
            mutexAgua.release();
        } catch (InterruptedException e) {
        }
    }

    private void guardarEnCajaV() {
        try {
            cajaVino.acquire();

            mutexVino.acquire();
            cantBotellasVino++;
            System.out.println(Thread.currentThread().getName()+" guardo una botella de vino en caja, la caja tiene "+cantBotellasVino);
            if (cantBotellasVino == 10) {
                etiquetaCaja = 'V';
                System.out.println("Se lleno la caja con botellas de vino");
                cajaLista.release();
                System.out.println("Se espera una caja de vino vacia");
                cajaVacia.acquire();
                System.out.println("Se reemplaza con una caja de vino vacia");
            }
            mutexVino.release();
        } catch (InterruptedException e) {
        }
    }

    public void guardarCaja() {
        try {
            cajaLista.acquire();

            mutexAlmacen.acquire();
            cantCajasAlmacen++;
            System.out.println("Se guarda 1 caja en el almacen, el almacen tiene "+cantCajasAlmacen+" cajas");
            if (cantCajasAlmacen == 10) {
                System.out.println("El almacen esta lleno");
                almacenLleno.release();
                System.out.println("Empaquetador espera que se vacie el almacen");
                almacenVacio.acquire();
            }
            mutexAlmacen.release();
        } catch (InterruptedException e) {
        }
    }

    public void reponerCaja() {
        if (etiquetaCaja == 'A') {
            cantBotellasAgua = 0;
            cajaAgua.release(10);
        } else {
            cantBotellasVino = 0;
            cajaVino.release(10);
        }
        cajaVacia.release();
    }

    public void repartirCajas() {
        try {
            almacenLleno.acquire();
            cantCajasAlmacen = 0;
            almacenVacio.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
