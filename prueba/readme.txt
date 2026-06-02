
## Requisitos previos

Verificar que estén instalados:

### Java

```bash
java -version
```

Debe mostrar Java 21 o superior.

### Maven

```bash
mvn -version
```

Debe mostrar Apache Maven correctamente instalado.

---

## Compilación del proyecto

Ubicarse en la carpeta del proyecto:

```bash
cd prueba
```

Compilar:

```bash
mvn clean compile
```

O generar el paquete completo:

```bash
mvn clean package
```

Si la compilación finaliza correctamente se generará la carpeta:

```text
target/
```

---

# Ejecución de la prueba TCP

## Iniciar Nodo 1

```bash
mvn exec:java -Dexec.mainClass="ec.edu.uteq.distribuidas.tcp.nodos.Nodo1TCP"
```

## Iniciar Nodo 2

Abrir otra terminal:

```bash
mvn exec:java -Dexec.mainClass="ec.edu.uteq.distribuidas.tcp.nodos.Nodo2TCP"
```

## Iniciar Nodo 3

Abrir otra terminal:

```bash
mvn exec:java -Dexec.mainClass="ec.edu.uteq.distribuidas.tcp.nodos.Nodo3TCP"
```

## Ejecutar prueba TCP

Abrir una cuarta terminal:

```bash
mvn exec:java -Dexec.mainClass="ec.edu.uteq.distribuidas.tcp.PruebaTCP"
```

La aplicación realizará intercambios de mensajes entre los nodos y registrará las métricas obtenidas.

---

# Ejecución de la prueba gRPC

## Iniciar Nodo 1

```bash
mvn exec:java -Dexec.mainClass="ec.edu.uteq.distribuidas.grpc.nodos.Nodo1Grpc"
```

## Iniciar Nodo 2

Abrir otra terminal:

```bash
mvn exec:java -Dexec.mainClass="ec.edu.uteq.distribuidas.grpc.nodos.Nodo2Grpc"
```

## Iniciar Nodo 3

Abrir otra terminal:

```bash
mvn exec:java -Dexec.mainClass="ec.edu.uteq.distribuidas.grpc.nodos.Nodo3Grpc"
```

## Ejecutar prueba gRPC

Abrir una cuarta terminal:

```bash
mvn exec:java -Dexec.mainClass="ec.edu.uteq.distribuidas.grpc.PruebaGrpc"
```

La aplicación realizará los intercambios utilizando gRPC y Protocol Buffers.

---

## Resultados

Los resultados son almacenados automáticamente en:

```text
src/main/resources/resultados/
```

Archivos generados:

```text
resultados_tcp.csv
resultados_grpc.csv
```

Estos archivos contienen métricas de latencia y desempeño obtenidas durante las pruebas.

---

## Implementación de Lamport

Cada nodo mantiene un reloj lógico de Lamport para garantizar el orden causal de los eventos distribuidos.

El proceso implementado es:

1. Incrementar el reloj antes de enviar un mensaje.
2. Adjuntar el valor del reloj al mensaje.
3. Al recibir un mensaje:
   - Actualizar el reloj utilizando:
   
   reloj = max(local, recibido) + 1

4. Registrar el nuevo valor del reloj.

Esto permite mantener consistencia temporal entre los nodos sin depender de relojes físicos sincronizados.

---
