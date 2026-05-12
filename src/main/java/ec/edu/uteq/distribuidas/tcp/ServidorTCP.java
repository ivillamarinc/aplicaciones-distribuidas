package ec.edu.uteq.distribuidas.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ServidorTCP {

    private static final int PUERTO = 9000;
    private static final int MAX_HILOS = 10;

    private static final AtomicInteger contadorClientes =
            new AtomicInteger(0);

    public static void main(String[] args) throws IOException {

        ExecutorService pool =
                Executors.newFixedThreadPool(MAX_HILOS);

        try (ServerSocket servidor =
                     new ServerSocket(PUERTO)) {

            System.out.println("[" + timestamp()
                    + "] Servidor listo en :" + PUERTO);

            while (true) {

                Socket cliente = servidor.accept();

                int id =
                        contadorClientes.incrementAndGet();

                System.out.println("[" + timestamp()
                        + "] Cliente #" + id
                        + " conectado desde "
                        + cliente.getInetAddress());

                pool.submit(
                        new ManejadorCliente(cliente, id));
            }
        }
    }

    public static String timestamp() {

        return LocalDateTime.now()
                .format(
                        DateTimeFormatter.ofPattern(
                                "HH:mm:ss.SSS"));
    }

    static class ManejadorCliente
            implements Runnable {

        private final Socket socket;
        private final int idCliente;

        ManejadorCliente(Socket socket,
                         int idCliente) {

            this.socket = socket;
            this.idCliente = idCliente;
        }

        @Override
        public void run() {

            try (

                    BufferedReader entrada =
                            new BufferedReader(
                                    new InputStreamReader(
                                            socket.getInputStream()));

                    PrintWriter salida =
                            new PrintWriter(
                                    socket.getOutputStream(),
                                    true)

            ) {

                String linea;

                while ((linea =
                        entrada.readLine()) != null) {

                    System.out.println("[" + timestamp()
                            + "] Cliente #"
                            + idCliente
                            + " > " + linea);

                    if (linea.equalsIgnoreCase(
                            "HORA")) {

                        salida.println(
                                "HORA_ACTUAL: "
                                        + timestamp());

                    } else if (
                            linea.toUpperCase()
                                    .startsWith("ECO")) {

                        String mensaje =
                                linea.length() > 4
                                        ? linea.substring(4)
                                        : "(vacio)";

                        salida.println(
                                "ECO [#"
                                        + idCliente
                                        + "]: "
                                        + mensaje);

                    } else if (
                            linea.equalsIgnoreCase(
                                    "SALIR")) {

                        salida.println(
                                "ADIOS cliente #"
                                        + idCliente);

                        System.out.println(
                                "Cliente #"
                                        + idCliente
                                        + " desconectado");

                        break;

                    } else {

                        salida.println(
                                "ERROR: comando desconocido");
                    }
                }

            } catch (IOException e) {

                System.err.println(
                        "Error cliente #"
                                + idCliente
                                + ": "
                                + e.getMessage());
            }
        }
    }
}