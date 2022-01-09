package com.west.todoAPI.controllers;

import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.repositories.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class TodoRestController {

    private final TodoRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoRestController.class);

    public TodoRestController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todos")
    public List<Todo> getTodos() throws InterruptedException {
        LOGGER.info("Finding all todos");
        TimeUnit.SECONDS.sleep(2);

        return repository.findAll();
    }

    @PostMapping("/todos")
    public Todo createTodo(@RequestBody Todo todo) {
        LOGGER.info("Creating todo: " + todo.toString());
        return repository.save(todo);
    }

    @PutMapping("/todos/{id}")
    public Todo updateTodo(@RequestBody Todo todo, @PathVariable("id") Long id) {
        Todo storedTodo = repository.getById(id);
        storedTodo.setDescription(todo.getDescription());
        storedTodo.setCompleted(todo.isCompleted());
        LOGGER.info("Updating todo with id: " + id + ". Updated todo: " + storedTodo.toString());
        return repository.save(storedTodo);
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodo(@PathVariable("id") Long id) {
        LOGGER.info("Deleting todo with id: " + id);
        repository.deleteById(id);
    }
}
