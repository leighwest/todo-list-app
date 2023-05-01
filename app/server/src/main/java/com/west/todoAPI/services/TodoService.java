package com.west.todoAPI.services;

import com.west.todoAPI.entities.Todo;

import java.util.List;

public interface TodoService {

    List<Todo> getTodos();

    Todo findById(Long id);

    Todo save(Todo todo);

    Todo update(Long id, Todo todo);

    void deleteById(Long idToDelete);
}
