package com.example.todolistapp;

public class Task {
    private String id;
    private String title;
    private String content;
    private String date;
    private String type;

    public Task(String id, String title, String content, String date, String type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }
}

