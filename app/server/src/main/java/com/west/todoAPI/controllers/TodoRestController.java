package com.west.todoAPI.controllers;

import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoRestController {

    @Autowired
    TodoRepository repository;

    @RequestMapping(value = "/todos", method= RequestMethod.GET)
    public List<Todo> getTodos() {
        return repository.findAll();
    }

    @RequestMapping(value = "/todos", method = RequestMethod.POST)
    public Todo createTodo(@RequestBody Todo todo) {
        return repository.save(todo);
    }

    @RequestMapping(value = "/todos", method = RequestMethod.PUT)
    public Todo updateTodo(@RequestBody Todo todo) {
        return repository.save(todo);
    }

    @RequestMapping(value = "/todos/{id}", method = RequestMethod.DELETE)
    public void deleteTodo(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
}
