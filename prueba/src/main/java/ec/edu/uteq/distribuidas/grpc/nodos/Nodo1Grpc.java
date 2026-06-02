package ec.edu.uteq.distribuidas.grpc.nodos;

import ec.edu.uteq.distribuidas.common.Nodo;
import ec.edu.uteq.distribuidas.grpc.ServidorGrpc;

public class Nodo1Grpc {

    public static void main(String[] args) {
        ServidorGrpc servidor = new ServidorGrpc(Nodo.NODO1);
        servidor.iniciar();
    }
}