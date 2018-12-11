package com.serzh.websocket.model;

public class ChatTestMessage {

    public ChatTestMessage() {
    }

    public ChatTestMessage(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
