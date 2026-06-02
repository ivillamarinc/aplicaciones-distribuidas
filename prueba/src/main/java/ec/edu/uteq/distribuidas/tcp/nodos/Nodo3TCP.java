package ec.edu.uteq.distribuidas.tcp.nodos;

import ec.edu.uteq.distribuidas.common.Nodo;
import ec.edu.uteq.distribuidas.tcp.ServidorTCP;

public class Nodo3TCP {

    public static void main(String[] args) {
        ServidorTCP servidor = new ServidorTCP(Nodo.NODO3);
        servidor.iniciar();
    }
}