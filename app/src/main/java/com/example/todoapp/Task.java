package com.example.todoapp;

public class Task {
    private String titulo;
    private String descricao;
    private boolean completed;
    private String dateCreated;

    public Task(String titulo, String descricao, boolean completed, String dateCreated) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.completed = completed;
        this.dateCreated = dateCreated;
    }

    public String getTitulo() {
        return title;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDateCreated() {
        return dateCreated;
    }
}
