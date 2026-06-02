package ec.edu.uteq.distribuidas.common;

public class Nodo {

    private final String nombre;
    private final int puertoTcp;
    private final int puertoGrpc;

    public static final Nodo NODO1 = new Nodo("Nodo1", 5000, 5000);
    public static final Nodo NODO2 = new Nodo("Nodo2", 5001, 5001);
    public static final Nodo NODO3 = new Nodo("Nodo3", 5002, 5002);

    public static final Nodo[] TODOS = {NODO1, NODO2, NODO3};

    public Nodo(String nombre, int puertoTcp, int puertoGrpc) {
        this.nombre = nombre;
        this.puertoTcp = puertoTcp;
        this.puertoGrpc = puertoGrpc;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuertoTcp() {
        return puertoTcp;
    }

    public int getPuertoGrpc() {
        return puertoGrpc;
    }
}