package com.west.todoAPI.controllers;

import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.repositories.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoRestController {

    private final TodoRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoRestController.class);

    public TodoRestController(TodoRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/todos", method= RequestMethod.GET)
    public List<Todo> getTodos() {
        LOGGER.info("Finding all todos");
        return repository.findAll();
    }

    @RequestMapping(value = "/todos", method = RequestMethod.POST)
    public Todo createTodo(@RequestBody Todo todo) {
        LOGGER.info("Creating todo: " + todo.toString());
        return repository.save(todo);
    }

    @RequestMapping(value = "/todos/{id}", method = RequestMethod.PUT)
    public Todo updateTodo(@RequestBody Todo todo, @PathVariable("id") Long id) {
        Todo storedTodo = repository.getById(id);
        storedTodo.setDescription(todo.getDescription());
        storedTodo.setCompleted(todo.isCompleted());
        LOGGER.info("Updating todo with id: " + id + ". Updated todo: " + storedTodo.toString());
        return repository.save(storedTodo);
    }

    @RequestMapping(value = "/todos/{id}", method = RequestMethod.DELETE)
    public void deleteTodo(@PathVariable("id") Long id) {
        LOGGER.info("Deleting todo with id: " + id);
        repository.deleteById(id);
    }
}
