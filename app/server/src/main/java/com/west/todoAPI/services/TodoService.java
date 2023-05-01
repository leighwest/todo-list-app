package com.west.todoAPI.services;

import com.west.todoAPI.entities.Todo;

import java.util.List;

public interface TodoService {

    List<Todo> getTodos();

    Todo findById(Long l);

    Todo save(Todo todo);

    void deleteById(Long idToDelete);
}
