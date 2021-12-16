package mastermind.controllers;

import java.time.LocalDateTime;

public class Error {

    //creating variables
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;

    /*

    getters and setters

     */

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
