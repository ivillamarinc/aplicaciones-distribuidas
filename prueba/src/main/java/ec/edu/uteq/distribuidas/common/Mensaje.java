package ec.edu.uteq.distribuidas.common;

public class Mensaje {

    private String sender;
    private int timestamp;
    private String message;

    public Mensaje() {
    }

    public Mensaje(String sender, int timestamp, String message) {
        this.sender = sender;
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}