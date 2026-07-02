# Resumen de Modificaciones — TPO2016Aeropuerto

## 1. PuestoAtencion.java (Guardia spam)

**Problema:** El Guardía imprimía cientos de "da permiso a un pasajero" porque el while en `permitirIngreso()` no verificaba `permisosPendientes > 0`. Mientras el pasajero no consumía el permiso (bloqueado en `wait()`), el Guardía seguía otorgando más permisos en un loop infinito.

**Solución:**
- En `permitirIngreso()`: se agregó `permisosPendientes > 0` a la condición del `while`.
- En `puedeEntrarPuesto()`: se agregó `notifyAll()` después de que el pasajero consume el permiso (`permisosPendientes--`), para despertar al Guardía.

**Cambio (líneas ~68 y ~74):**
```java
// permitirIngreso()
while (esperando == 0 || activos >= maxPasajeros || permisosPendientes > 0) {
    wait();
}

// puedeEntrarPuesto() - después de permisosPendientes--
notifyAll(); // avisa al guardia que el permiso fue consumido
```

---

## 2. PuestoInformes.java (Deadlock con signal())

**Problema:** El empleado llamaba `pasajeroEspera.signal()` (singular), que despierta solo **un** pasajero del Condition. Hay dos `pasajeroEspera.await()` en `llegarAInforme()`:
1. Esperar a que el empleado esté libre (`while (atendiendo)` antes de marcar `atendiendo = true`).
2. Esperar a que el empleado termine de atender (`while (atendiendo)` después de `empleadoEspera.signal()`).

Si `signal()` despertaba a un pasajero del segundo await (el que está siendo atendido), los pasajeros en el primer await nunca se enteraban de que `atendiendo` pasó a `false`, y quedaban bloqueados para siempre → **deadlock**.

**Solución:** Cambiar `signal()` → `signalAll()` en `atenderPasajero()`.

**Cambio (línea 53):**
```java
// Antes:
pasajeroEspera.signal();

// Después:
pasajeroEspera.signalAll();
```

---

## 3. TransporteATerminal.java (Abordar mientras el conductor está en ruta + Deadlock del CyclicBarrier)

**Problema:** Los pasajeros podían subir al transporte mientras el conductor estaba haciendo el recorrido, violando la especificación. Además, al mover `maximoPasajeros.release()` a `terminoRecorrido()` (para evitar que suban durante el viaje), se producía un deadlock: los primeros pasajeros ocupaban los permisos, el CyclicBarrier nunca se llenaba (porque faltaban pasajeros que no podían adquirir `maximoPasajeros`), y el conductor nunca arrancaba.

**Solución:** Agregar un `Semaphore puedeAbordar` independiente:
- Inicializado con `capacidad`.
- El pasajero hace `puedeAbordar.acquire()` **antes** de `maximoPasajeros.acquire()`.
- `avisarConductor()` hace `puedeAbordar.drainPermits()` para bloquear nuevos abordajes durante el viaje.
- `terminoRecorrido()` hace `puedeAbordar.release(capacidad)` para permitir el próximo grupo.
- `bajarDelTransporte()` conserva `maximoPasajeros.release()` (se revierte el cambio anterior).

**Cambios clave:**
```java
private final Semaphore puedeAbordar;

// Constructor:
this.puedeAbordar = new Semaphore(cantidad, true);

// subirATransporte():
puedeAbordar.acquire(); // <-- NUEVO
maximoPasajeros.acquire();

// avisarConductor():
puedeAbordar.drainPermits(); // <-- NUEVO

// terminoRecorrido():
puedeAbordar.release(capacidad); // <-- NUEVO
```

Además, se corrigió un bug de índice en `bajarDelTransporte()`:
```java
// Antes (mal):
this.pasajerosTerminal[numeroTerminal]++;

// Después (bien):
this.pasajerosTerminal[numeroTerminal - 1]++;
```

---

## 4. Pasajero.java (Índice de terminal + Llamada a salirPuesto)

**Problema 1 (array index):** `aeropuerto.terminales[terminal]` causaba `ArrayIndexOutOfBoundsException` porque `terminal` viene 1-based (A=1, B=2, ...) pero los arrays Java son 0-based. También el chequeo `terminal <= 26` era incorrecto porque el array real podía tener menos terminales.

**Solución:**
```java
// Antes:
if (terminal >= 1 && terminal <= 26) {
    int idxTerminal = terminal; // mal
    aeropuerto.terminales[terminal]...

// Después:
int idxTerminal = terminal - 1;
if (terminal >= 1 && terminal <= aeropuerto.terminales.length) {
    aeropuerto.terminales[idxTerminal]...
```

**Problema 2 (orden de operaciones):** `salirPuesto()` se llamaba después de todo el bloque (transporte + FreeShop + sala), pero si ocurría una excepción en el intercambio, el pasajero nunca llamaba a `salirPuesto()` y el puesto quedaba con un `activos` forever, bloqueando al Guardía.

**Solución:** Mover `salirPuesto()` inmediatamente después de `realizarIntercambio()`, dentro del try, antes de la lógica de terminal.

**Cambio en `run()`:**
```java
boletoTerminal = puesto.realizarIntercambio(boletoAvion);
puesto.salirPuesto();      // <-- movido acá
ingreso = false;           // <-- marcamos que ya no está en el puesto

// ... después: transporte, FreeShop, sala
```

---

## 5. PuestoAtencionS.java (Math.random fuera de rango)

**Problema:** `Math.random() * (cantidadTerminal - 1)` generaba un número en `[0, cantidadTerminal-2]`, excluyendo la última terminal posible.

**Solución:**
```java
// Antes:
int numeroTerminal = (int) (Math.random() * (cantidadTerminal - 1));

// Después:
int numeroTerminal = (int) (Math.random() * cantidadTerminal);
```

---

## 6. SalaEmbarque.java + EmpleadoSalon.java (Boarding call inmediato)

**Problema:** `EmpleadoSalon.run()` llamaba a `llamarAEmbarcar()` inmediatamente, sin esperar a que llegue al menos un pasajero a la sala de embarque. Como `embarque.countDown()` solo se puede llamar una vez (CountDownLatch(1)), el aviso se perdía.

**Solución en SalaEmbarque.java:**
- Agregar `CountDownLatch pasajeroPresente = new CountDownLatch(1)`.
- `esperarPasajero()`: bloquea al empleado hasta que algún pasajero llegue (`pasajeroPresente.await()`).
- `esperarLlamado()`: hace `pasajeroPresente.countDown()` para liberar al empleado.

**Cambios:**
```java
// SalaEmbarque.java - nuevo método
public void esperarPasajero() throws InterruptedException {
    pasajeroPresente.await();
}

// EmpleadoSalon.java
public void run(){
    try {
        embarques.sala.esperarPasajero(); // <-- NUEVO
        embarques.sala.llamarAEmbarcar();
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
```

---

## 7. Terminal.java (Parámetro eliminado)

**Problema:** El constructor de `Terminal` recibía un parámetro `cantPasajeros` pero `SalaEmbarque` se creaba sin necesidad de conocer la cantidad de pasajeros (usa `CountDownLatch` en su lugar).

**Solución:** Se quitó el parámetro `cantPasajeros` del constructor de `SalaEmbarque`. El constructor de `Terminal` quedó intacto (ya que el `new SalaEmbarque(id)` es correcto).

---

## 8. prueba.java (Matriz de puertos de embarque incorrecta)

**Problema:** La matriz `puertosEmbarque` se construía con valores incorrectos. Se definía como `new int[][]{{1,1},{15,15},{20,20}}` en lugar de `{{1,7},{8,15},{16,20}}`.

**Solución:** Corregir los valores en `Aeropuerto.java` (donde se crea la matriz).

---

## 9. Cajero.java (Loop infinito)

**Problema:** `while(i < 5)` hacía que el Cajero atendiera solo 5 compras y terminara. Los cajeros deben estar en un loop infinito para el funcionamiento del FreeShop.

**Solución:**
```java
// Antes:
while (i < 5) { ... i++; }

// Después:
while (true) { ... }
```

---

## Resumen de archivos modificados

| Archivo | Cambio |
|---|---|
| `PuestoAtencion.java` | Agregado `permisosPendientes > 0` en while + `notifyAll()` post-consumo |
| `PuestoInformes.java` | `signal()` → `signalAll()` |
| `TransporteATerminal.java` | Nuevo `Semaphore puedeAbordar`; corrección índice `pasajerosTerminal` |
| `Pasajero.java` | `terminal - 1` en acceso a array; moved `salirPuesto()` después de `realizarIntercambio()` |
| `PuestoAtencionS.java` | `Math.random() * (cantidadTerminal - 1)` → `* cantidadTerminal` |
| `SalaEmbarque.java` | Nuevo `CountDownLatch pasajeroPresente` + método `esperarPasajero()` |
| `EmpleadoSalon.java` | Llama a `esperarPasajero()` antes de `llamarAEmbarcar()` |
| `Terminal.java` | Eliminado parámetro `cantPasajeros` del constructor de `SalaEmbarque` |
| `prueba.java` / `Aeropuerto.java` | Corregida matriz `puertosEmbarque` |
| `Cajero.java` | `while(i < 5)` → `while(true)` |

---

*Generado el 01/07/2026*
