package ec.edu.uteq.distribuidas.tcp;

import ec.edu.uteq.distribuidas.common.CsvLogger;
import ec.edu.uteq.distribuidas.common.Mensaje;
import ec.edu.uteq.distribuidas.common.Nodo;
import ec.edu.uteq.distribuidas.common.RelojLamport;

public class PruebaTCP {

    public static void main(String[] args) {
        System.out.println("=== PRUEBA TCP CON 3 CLIENTES Y RELOJ DE LAMPORT ===");

        ClienteLogico cliente1 = new ClienteLogico("Cliente1");
        ClienteLogico cliente2 = new ClienteLogico("Cliente2");
        ClienteLogico cliente3 = new ClienteLogico("Cliente3");

        ClienteLogico[] clientes = {cliente1, cliente2, cliente3};

        ejecutar20Intercambios(clientes);
        medirLatencia100Envios(clientes);
    }

    private static void ejecutar20Intercambios(ClienteLogico[] clientes) {
        System.out.println("\n--- Ejecutando 20 intercambios TCP ---");

        Nodo[] destinos = {
                Nodo.NODO1,
                Nodo.NODO2,
                Nodo.NODO3
        };

        for (int i = 1; i <= 20; i++) {
            ClienteLogico cliente = clientes[(i - 1) % clientes.length];
            Nodo destino = destinos[i % destinos.length];

            Mensaje respuesta = cliente.enviar(destino, "Intercambio TCP #" + i);

            if (respuesta != null) {
                cliente.actualizarReloj(respuesta.getTimestamp());

                System.out.println("Intercambio #" + i
                        + " | " + cliente.getNombre()
                        + " envio a " + destino.getNombre()
                        + " | respuesta: " + respuesta.getMessage()
                        + " | reloj cliente: " + cliente.getTiempoReloj());
            }
        }
    }

    private static void medirLatencia100Envios(ClienteLogico[] clientes) {
        System.out.println("\n--- Midiendo latencia promedio de 100 envios TCP ---");

        long sumaLatenciasNs = 0;
        int enviosCorrectos = 0;

        Nodo[] destinos = {
                Nodo.NODO1,
                Nodo.NODO2,
                Nodo.NODO3
        };

        for (int i = 1; i <= 100; i++) {
            ClienteLogico cliente = clientes[(i - 1) % clientes.length];
            Nodo destino = destinos[i % destinos.length];

            long inicio = System.nanoTime();

            Mensaje respuesta = cliente.enviar(destino, "Mensaje de latencia TCP #" + i);

            long fin = System.nanoTime();

            if (respuesta != null) {
                cliente.actualizarReloj(respuesta.getTimestamp());
                sumaLatenciasNs += (fin - inicio);
                enviosCorrectos++;
            }
        }

        if (enviosCorrectos == 0) {
            System.out.println("No hubo envios correctos. Verifique que los nodos esten ejecutandose.");
            return;
        }

        double promedioMs = (sumaLatenciasNs / 1_000_000.0) / enviosCorrectos;

        System.out.println("Envios correctos: " + enviosCorrectos);
        System.out.println("Latencia promedio TCP: " + promedioMs + " ms");

        CsvLogger.guardarResultado(
                "src/main/resources/resultados/resultados_tcp.csv",
                "TCP",
                enviosCorrectos,
                promedioMs
        );

        System.out.println("Resultado guardado en src/main/resources/resultados/resultados_tcp.csv");
    }

    private static class ClienteLogico {

        private final String nombre;
        private final RelojLamport reloj;

        public ClienteLogico(String nombre) {
            this.nombre = nombre;
            this.reloj = new RelojLamport();
        }

        public Mensaje enviar(Nodo destino, String texto) {
            Mensaje mensaje = new Mensaje(
                    nombre,
                    reloj.incrementar(),
                    texto
            );

            return ClienteTCP.enviarMensaje(destino, mensaje);
        }

        public void actualizarReloj(int timestampRecibido) {
            reloj.actualizar(timestampRecibido);
        }

        public String getNombre() {
            return nombre;
        }

        public int getTiempoReloj() {
            return reloj.getTiempo();
        }
    }
}