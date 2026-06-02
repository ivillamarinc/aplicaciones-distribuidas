package ec.edu.uteq.distribuidas.common;

public class RelojLamport {

    private int tiempo;

    public RelojLamport() {
        this.tiempo = 0;
    }

    public synchronized int incrementar() {
        tiempo++;
        return tiempo;
    }

    public synchronized int actualizar(int recibido) {
        tiempo = Math.max(tiempo, recibido) + 1;
        return tiempo;
    }

    public synchronized int getTiempo() {
        return tiempo;
    }
}