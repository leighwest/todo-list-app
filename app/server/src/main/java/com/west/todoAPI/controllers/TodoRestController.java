package com.west.todoAPI.controllers;

import com.west.todoAPI.entities.ValidationError;
import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.services.TodoServiceImpl;
import com.west.todoAPI.validator.TodoValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;


@RestController
@Tag(name="Todo Rest Endpoint")
public class TodoRestController {

    private final TodoServiceImpl todoService;
    private final TodoValidator tv;

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoRestController.class);

    public TodoRestController(TodoServiceImpl todoService, TodoValidator tv) {
        this.todoService = todoService;
        this.tv = tv;
    }

    @GetMapping("/todos")
    @Operation(summary="Returns a list of todos")
    public List<Todo> getTodos() throws InterruptedException {
        LOGGER.info("Finding all todos");
        TimeUnit.SECONDS.sleep(2);

        return todoService.getTodos();
    }


    @PostMapping("/todos")
    @Operation(summary="Creates an individual todo")
    public @ApiResponse(description="Todo object") ResponseEntity<Object> createTodo(@Valid @RequestBody Todo todo,
                                                                                   BindingResult bindingResult) {

        Optional<String> validationMessage = tv.validate(todo);

        if (validationMessage.isPresent()) {
            ValidationError validationError = new ValidationError(validationMessage.get());
            return ResponseEntity.badRequest().body(validationError);
        }

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                LOGGER.info(objectError.toString());
            });
        }

        LOGGER.info("Creating todo: " + todo);
        todoService.save(todo);

        return ResponseEntity.accepted().body(todo);
    }


    @PutMapping("/todos/{id}")
    @Operation(summary="Updates an individual todo by ID")
    public @ApiResponse(description="Todo object") Todo updateTodo(@Parameter(description="ID of the todo") @RequestBody Todo todo, @PathVariable("id") Long id) {
        Todo storedTodo = todoService.findById(id);
        storedTodo.setDescription(todo.getDescription());
        storedTodo.setCompleted(todo.isCompleted());
        LOGGER.info("Updating todo with id: " + id + ". Updated todo: " + storedTodo);
        return todoService.save(storedTodo);
    }

    @DeleteMapping("/todos/{id}")
    @Operation(summary="Deletes an individual todo by ID")
    public void deleteTodo(@Parameter(description="ID of the todo") @PathVariable("id") Long id) {
        LOGGER.info("Deleting todo with id: " + id);
        todoService.deleteById(id);
    }
}
