package com.example.educationforeveryone.ui.Chats;

public class ChatModel {

    private String username, userId;

    public ChatModel(String username, String userId) {
        this.username = username;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
