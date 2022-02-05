package com.west.todoAPI.controllers;

import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.services.TodoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;


@RestController
public class TodoRestController {

    private final TodoServiceImpl todoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoRestController.class);

    public TodoRestController(TodoServiceImpl todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public List<Todo> getTodos() throws InterruptedException {
        LOGGER.info("Finding all todos");
        TimeUnit.SECONDS.sleep(2);

        return todoService.getTodos();
    }


    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                LOGGER.info(objectError.toString());
            });
        }

        LOGGER.info("Creating todo: " + todo.toString());
        todoService.save(todo);
        return ResponseEntity.accepted().body(todo);
    }

    @PutMapping("/todos/{id}")
    public Todo updateTodo(@RequestBody Todo todo, @PathVariable("id") Long id) {
        Todo storedTodo = todoService.findById(id);
        storedTodo.setDescription(todo.getDescription());
        storedTodo.setCompleted(todo.isCompleted());
        LOGGER.info("Updating todo with id: " + id + ". Updated todo: " + storedTodo);
        return todoService.save(storedTodo);
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodo(@PathVariable("id") Long id) {

        LOGGER.info("Deleting todo with id: " + id);

        todoService.deleteById(id);
    }
}
