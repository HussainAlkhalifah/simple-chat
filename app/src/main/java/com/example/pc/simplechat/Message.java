package com.example.pc.simplechat;

public class Message {
    private String content;
 /*   private String email;

    public Message(String content, String email) {
        this.content = content;
        this.email = email;
    }*/

    public Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
/*
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }*/

    public Message() {
    }

    @Override
    public String toString() {
        return getContent();
    }
}
