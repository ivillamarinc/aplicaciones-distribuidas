package ec.edu.uteq.distribuidas.tcp;

import com.google.gson.Gson;
import ec.edu.uteq.distribuidas.common.Mensaje;
import ec.edu.uteq.distribuidas.common.Nodo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteTCP {

    private static final String HOST = "localhost";
    private static final Gson gson = new Gson();

    public static Mensaje enviarMensaje(Nodo destino, Mensaje mensaje) {
        try (
                Socket socket = new Socket(HOST, destino.getPuertoTcp());
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            String json = gson.toJson(mensaje);

            salida.println(json);

            String respuestaJson = entrada.readLine();

            return gson.fromJson(respuestaJson, Mensaje.class);

        } catch (Exception e) {
            System.out.println("Error enviando mensaje TCP a "
                    + destino.getNombre()
                    + " por puerto "
                    + destino.getPuertoTcp()
                    + ": "
                    + e.getMessage());

            return null;
        }
    }
}