package ec.edu.uteq.distribuidas.grpc;

import ec.edu.uteq.distribuidas.common.Nodo;
import ec.edu.uteq.distribuidas.grpc.proto.ComunicacionServiceGrpc;
import ec.edu.uteq.distribuidas.grpc.proto.MensajeRequest;
import ec.edu.uteq.distribuidas.grpc.proto.MensajeResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ClienteGrpc {

    private static final String HOST = "localhost";

    public static MensajeResponse enviarMensaje(Nodo destino, String sender, int timestamp, String message) {
        ManagedChannel canal = ManagedChannelBuilder
                .forAddress(HOST, destino.getPuertoGrpc())
                .usePlaintext()
                .build();

        try {
            ComunicacionServiceGrpc.ComunicacionServiceBlockingStub stub =
                    ComunicacionServiceGrpc.newBlockingStub(canal);

            MensajeRequest request = MensajeRequest.newBuilder()
                    .setSender(sender)
                    .setTimestamp(timestamp)
                    .setMessage(message)
                    .build();

            return stub.enviarMensaje(request);

        } catch (Exception e) {
            System.out.println("Error enviando mensaje gRPC a "
                    + destino.getNombre()
                    + " por puerto "
                    + destino.getPuertoGrpc()
                    + ": "
                    + e.getMessage());

            return null;

        } finally {
            canal.shutdown();
        }
    }
}