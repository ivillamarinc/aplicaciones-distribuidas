package ec.edu.uteq.distribuidas.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {

    private static final String HOST = "localhost";
    private static final int PUERTO = 9000;

    public static void main(String[] args)
            throws IOException {

        try (

                Socket socket =
                        new Socket(HOST, PUERTO);

                BufferedReader entrada =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()));

                PrintWriter salida =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true);

                Scanner teclado =
                        new Scanner(System.in)

        ) {

            System.out.println(
                    "Conectado a "
                            + HOST
                            + ":"
                            + PUERTO);

            System.out.println("Comandos:");
            System.out.println("HORA");
            System.out.println("ECO mensaje");
            System.out.println("SALIR");

            while (true) {

                System.out.print("> ");

                String comando =
                        teclado.nextLine().trim();

                if (comando.isEmpty()) {
                    continue;
                }

                salida.println(comando);

                String respuesta =
                        entrada.readLine();

                System.out.println(
                        "Servidor: "
                                + respuesta);

                if (comando.equalsIgnoreCase(
                        "SALIR")) {

                    break;
                }
            }
        }

        System.out.println(
                "Conexion cerrada.");
    }
}