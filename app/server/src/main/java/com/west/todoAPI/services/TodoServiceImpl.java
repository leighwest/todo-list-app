package com.west.todoAPI.services;

import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.repositories.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoServiceImpl.class);

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
        Optional<Todo> todoOptional = todoRepository.findById(l);

        if (!todoOptional.isPresent()) {
            throw new RuntimeException("Todo not found!");
        }
        return todoOptional.get();
    }

    @Override
    public Todo save(Todo todo) {
        Todo savedTodo = todoRepository.save(todo);
        LOGGER.info("Saving todo with ID: " + savedTodo.getId());
        return savedTodo;
    }

    @Override
    public void deleteById(Long idToDelete) {
        todoRepository.deleteById(idToDelete);
    }
}
