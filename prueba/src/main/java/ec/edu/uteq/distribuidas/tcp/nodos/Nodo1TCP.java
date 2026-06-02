package ec.edu.uteq.distribuidas.tcp.nodos;

import ec.edu.uteq.distribuidas.common.Nodo;
import ec.edu.uteq.distribuidas.tcp.ServidorTCP;

public class Nodo1TCP {

    public static void main(String[] args) {
        ServidorTCP servidor = new ServidorTCP(Nodo.NODO1);
        servidor.iniciar();
    }
}