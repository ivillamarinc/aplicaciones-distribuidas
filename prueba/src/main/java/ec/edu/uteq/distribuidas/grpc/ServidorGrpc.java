package ec.edu.uteq.distribuidas.grpc;

import ec.edu.uteq.distribuidas.common.Nodo;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ServidorGrpc {

    private final Nodo nodo;

    public ServidorGrpc(Nodo nodo) {
        this.nodo = nodo;
    }

    public void iniciar() {
        try {
            Server server = ServerBuilder
                    .forPort(nodo.getPuertoGrpc())
                    .addService(new ServicioGrpc(nodo))
                    .build()
                    .start();

            System.out.println(nodo.getNombre() + " escuchando por gRPC en puerto " + nodo.getPuertoGrpc());

            server.awaitTermination();

        } catch (Exception e) {
            System.out.println("Error en servidor gRPC " + nodo.getNombre() + ": " + e.getMessage());
        }
    }
}