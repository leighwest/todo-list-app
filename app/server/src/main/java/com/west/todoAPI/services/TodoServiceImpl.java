package com.west.todoAPI.services;

import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getTodos() {

        List<Todo> todoList = new ArrayList<>();
        todoRepository.findAll().iterator().forEachRemaining(todoList::add);
        return todoList;
    }

    @Override
    public Todo findById(Long l) {
        return null;
    }

    @Override
    public Todo save(Todo todo) {
        return null;
    }

    @Override
    public void deleteById(Long idToDelete) {

    }
}
