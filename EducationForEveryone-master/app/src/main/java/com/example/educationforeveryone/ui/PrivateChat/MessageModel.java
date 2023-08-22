package com.example.educationforeveryone.ui.PrivateChat;

public class MessageModel {

    private String sender, text, time;

    public MessageModel(String sender, String text, String time) {
        this.sender = sender;
        this.text = text;
        this.time = time;
    }

    public MessageModel(){}

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
