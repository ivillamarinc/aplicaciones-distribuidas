package ec.edu.uteq.distribuidas.tcp;

import com.google.gson.Gson;
import ec.edu.uteq.distribuidas.common.Mensaje;
import ec.edu.uteq.distribuidas.common.Nodo;
import ec.edu.uteq.distribuidas.common.RelojLamport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {

    private static final Gson gson = new Gson();

    private final Nodo nodo;
    private final RelojLamport reloj;

    public ServidorTCP(Nodo nodo) {
        this.nodo = nodo;
        this.reloj = new RelojLamport();
    }

    public void iniciar() {
        try (ServerSocket servidor = new ServerSocket(nodo.getPuertoTcp())) {
            System.out.println(nodo.getNombre() + " escuchando por TCP en puerto " + nodo.getPuertoTcp());

            while (true) {
                Socket socket = servidor.accept();
                new Thread(() -> atenderCliente(socket)).start();
            }

        } catch (Exception e) {
            System.out.println("Error en servidor TCP " + nodo.getNombre() + ": " + e.getMessage());
        }
    }

    private void atenderCliente(Socket socket) {
        try (
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String jsonRecibido = entrada.readLine();

            Mensaje mensaje = gson.fromJson(jsonRecibido, Mensaje.class);

            int relojActualizado = reloj.actualizar(mensaje.getTimestamp());

            System.out.println("[" + nodo.getNombre() + "] Mensaje recibido");
            System.out.println("Remitente: " + mensaje.getSender());
            System.out.println("Timestamp recibido: " + mensaje.getTimestamp());
            System.out.println("Reloj local actualizado: " + relojActualizado);
            System.out.println("Mensaje: " + mensaje.getMessage());
            System.out.println("--------------------------------------");

            Mensaje respuesta = new Mensaje(
                    nodo.getNombre(),
                    reloj.incrementar(),
                    "ACK recibido por " + nodo.getNombre()
            );

            salida.println(gson.toJson(respuesta));

        } catch (Exception e) {
            System.out.println("Error atendiendo cliente en " + nodo.getNombre() + ": " + e.getMessage());
        }
    }
}