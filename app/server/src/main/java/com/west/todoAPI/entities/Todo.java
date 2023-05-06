package com.west.todoAPI.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID uuid;

    private String description;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    private boolean completed;

    public Todo() {}

    public Todo(UUID uuid, String description, LocalDate date, boolean completed) {
        this.uuid = uuid;
        this.description = description;
        this.date = date;
        this.completed = completed;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "description='" + description + '\'' +
                ", date=" + date +
                ", completed=" + completed +
                '}';
    }
}
