package ec.edu.uteq.distribuidas.tcp.nodos;

import ec.edu.uteq.distribuidas.common.Nodo;
import ec.edu.uteq.distribuidas.tcp.ServidorTCP;

public class Nodo2TCP {

    public static void main(String[] args) {
        ServidorTCP servidor = new ServidorTCP(Nodo.NODO2);
        servidor.iniciar();
    }
}