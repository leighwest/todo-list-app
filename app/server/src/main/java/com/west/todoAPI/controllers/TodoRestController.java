package com.west.todoAPI.controllers;

import com.west.todoAPI.dto.TodoDto;
import com.west.todoAPI.dto.request.InitialTodoRequestModel;
import com.west.todoAPI.dto.request.UpdateTodoRequestModel;
import com.west.todoAPI.services.TodoService;
import com.west.todoAPI.validator.TodoValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;


@RestController
@Tag(name="Todo Rest Endpoint")
public class TodoRestController {

    private final TodoService todoService;
    private final TodoValidator tv;
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoRestController.class);

    public TodoRestController(TodoService todoService, TodoValidator tv) {
        this.todoService = todoService;
        this.tv = tv;
    }

    @GetMapping(path="/todos", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary="Returns a list of todos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "No todos found")})
    public ResponseEntity<List<TodoDto>> getTodos() throws InterruptedException {
        LOGGER.info("Finding all todos");
        TimeUnit.SECONDS.sleep(1);

        List<TodoDto> todoList = todoService.getTodos();

        if (todoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(todoList);
    }


    @PostMapping(path="/todos", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary="Creates an individual todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Creates a todo", content =
                    { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    public @ApiResponse(description="Todo object") ResponseEntity<Object> createTodo(@RequestBody InitialTodoRequestModel todoDetails) {
        Optional<String> validationMessage = tv.validate(todoDetails);

        if (validationMessage.isPresent()) {
            return ResponseEntity.badRequest().body(validationMessage);
        }

        LOGGER.info("Creating todo: " + todoDetails);
        TodoDto savedTodo = todoService.save(todoDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTodo);
    }


    @PutMapping(path="/todos/{uuid}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary="Updates an individual todo by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updates a todo", content =
                    { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Resource not found")})
    public @ApiResponse(description="Todo object") ResponseEntity<TodoDto> updateTodo(@Parameter(description="ID of the todo") @PathVariable("uuid") UUID uuid, @RequestBody UpdateTodoRequestModel todo) {
        TodoDto updatedTodo = todoService.update(uuid, todo);

        return ResponseEntity.status(HttpStatus.OK).body(updatedTodo);
    }

    @DeleteMapping(path="/todos/{uuid}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary="Deletes an individual todo by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletes a todo", content =
                    { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Resource not found")})
    public void deleteTodo(@Parameter(description="UUID of the todo") @PathVariable("uuid") UUID uuid) {
        LOGGER.info("Deleting todo with uuid: " + uuid);
        todoService.deleteByUuid(uuid);
    }
}
