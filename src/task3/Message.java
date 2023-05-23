package task3;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    private long date;

    public boolean isServerMessage;
    public Message(String message, long date, boolean isServerMessage) {
        this.message = message;
        this.date = date;
        this.isServerMessage = isServerMessage;
    }
    public String getMessage() {
        return message;
    }

    public long getDate() {
        return date;
    }
}
