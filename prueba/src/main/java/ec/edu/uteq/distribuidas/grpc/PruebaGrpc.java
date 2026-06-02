package ec.edu.uteq.distribuidas.grpc;

import ec.edu.uteq.distribuidas.common.CsvLogger;
import ec.edu.uteq.distribuidas.common.Nodo;
import ec.edu.uteq.distribuidas.common.RelojLamport;
import ec.edu.uteq.distribuidas.grpc.proto.MensajeResponse;

public class PruebaGrpc {

    public static void main(String[] args) {
        System.out.println("=== PRUEBA gRPC CON 3 CLIENTES Y RELOJ DE LAMPORT ===");

        ClienteLogico cliente1 = new ClienteLogico("Cliente1");
        ClienteLogico cliente2 = new ClienteLogico("Cliente2");
        ClienteLogico cliente3 = new ClienteLogico("Cliente3");

        ClienteLogico[] clientes = {cliente1, cliente2, cliente3};

        ejecutar20Intercambios(clientes);
        medirLatencia100Envios(clientes);
    }

    private static void ejecutar20Intercambios(ClienteLogico[] clientes) {
        System.out.println("\n--- Ejecutando 20 intercambios gRPC ---");

        Nodo[] destinos = {
                Nodo.NODO1,
                Nodo.NODO2,
                Nodo.NODO3
        };

        for (int i = 1; i <= 20; i++) {
            ClienteLogico cliente = clientes[(i - 1) % clientes.length];
            Nodo destino = destinos[i % destinos.length];

            MensajeResponse respuesta = cliente.enviar(destino, "Intercambio gRPC #" + i);

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
        System.out.println("\n--- Midiendo latencia promedio de 100 envios gRPC ---");

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

            MensajeResponse respuesta = cliente.enviar(destino, "Mensaje de latencia gRPC #" + i);

            long fin = System.nanoTime();

            if (respuesta != null) {
                cliente.actualizarReloj(respuesta.getTimestamp());
                sumaLatenciasNs += (fin - inicio);
                enviosCorrectos++;
            }
        }

        if (enviosCorrectos == 0) {
            System.out.println("No hubo envios correctos. Verifique que los nodos gRPC esten ejecutandose.");
            return;
        }

        double promedioMs = (sumaLatenciasNs / 1_000_000.0) / enviosCorrectos;

        System.out.println("Envios correctos: " + enviosCorrectos);
        System.out.println("Latencia promedio gRPC: " + promedioMs + " ms");

        CsvLogger.guardarResultado(
                "src/main/resources/resultados/resultados_grpc.csv",
                "gRPC",
                enviosCorrectos,
                promedioMs
        );

        System.out.println("Resultado guardado en src/main/resources/resultados/resultados_grpc.csv");
    }

    private static class ClienteLogico {

        private final String nombre;
        private final RelojLamport reloj;

        public ClienteLogico(String nombre) {
            this.nombre = nombre;
            this.reloj = new RelojLamport();
        }

        public MensajeResponse enviar(Nodo destino, String texto) {
            return ClienteGrpc.enviarMensaje(
                    destino,
                    nombre,
                    reloj.incrementar(),
                    texto
            );
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