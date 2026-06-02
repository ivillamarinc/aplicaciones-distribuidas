package ec.edu.uteq.distribuidas.grpc;

import ec.edu.uteq.distribuidas.common.Nodo;
import ec.edu.uteq.distribuidas.common.RelojLamport;
import ec.edu.uteq.distribuidas.grpc.proto.ComunicacionServiceGrpc;
import ec.edu.uteq.distribuidas.grpc.proto.MensajeRequest;
import ec.edu.uteq.distribuidas.grpc.proto.MensajeResponse;
import io.grpc.stub.StreamObserver;

public class ServicioGrpc extends ComunicacionServiceGrpc.ComunicacionServiceImplBase {

    private final Nodo nodo;
    private final RelojLamport reloj;

    public ServicioGrpc(Nodo nodo) {
        this.nodo = nodo;
        this.reloj = new RelojLamport();
    }

    @Override
    public void enviarMensaje(MensajeRequest request, StreamObserver<MensajeResponse> responseObserver) {

        int relojActualizado = reloj.actualizar(request.getTimestamp());

        System.out.println("[" + nodo.getNombre() + "] Mensaje gRPC recibido");
        System.out.println("Remitente: " + request.getSender());
        System.out.println("Timestamp recibido: " + request.getTimestamp());
        System.out.println("Reloj local actualizado: " + relojActualizado);
        System.out.println("Mensaje: " + request.getMessage());
        System.out.println("--------------------------------------");

        MensajeResponse response = MensajeResponse.newBuilder()
                .setSender(nodo.getNombre())
                .setTimestamp(reloj.incrementar())
                .setMessage("ACK gRPC recibido por " + nodo.getNombre())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}