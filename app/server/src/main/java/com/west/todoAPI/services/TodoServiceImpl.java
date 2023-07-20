package com.west.todoAPI.services;

import com.west.todoAPI.dto.TodoDto;
import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.dto.request.InitialTodoRequestModel;
import com.west.todoAPI.dto.request.UpdateTodoRequestModel;
import com.west.todoAPI.repositories.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    private final Transformer transformer;

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoServiceImpl.class);

    public TodoServiceImpl(TodoRepository todoRepository, Transformer transformer) {
        this.todoRepository = todoRepository;
        this.transformer = transformer;
    }

    @Override
    public List<TodoDto> getTodos() {

        List<TodoDto> todoList = new ArrayList<>();
        todoRepository.findAll().stream().map(transformer::transformToDto).iterator().forEachRemaining(todoList::add);
        return todoList;
    }

    public Optional<TodoDto> findByUuid(UUID uuid) {
        Optional<Todo> todoOptional = todoRepository.findByUuid(uuid);

        return todoOptional.map(transformer::transformToDto);

    }

    @Override
    public TodoDto save(InitialTodoRequestModel initialTodo) {
        Todo todo = new Todo(UUID.randomUUID(), initialTodo.getDescription(), initialTodo.getDate(), false);

        Todo savedTodo = todoRepository.save(todo);
        LOGGER.info("Saving todo with ID: " + savedTodo.getId());
        return transformer.transformToDto(todo);
    }

    @Override
    public TodoDto update(UUID uuid, UpdateTodoRequestModel todo) {

        Todo storedTodo = todoRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found!"));

        storedTodo.setDescription(todo.getDescription());
        storedTodo.setCompleted(todo.getCompleted());
        storedTodo.setDate(todo.getDate());

        Todo updatedTodo = todoRepository.save(storedTodo);
        LOGGER.info("Updating todo with ID: " + updatedTodo.getId());

        return transformer.transformToDto(updatedTodo);
    }

    public void deleteByUuid(UUID idToDelete) {
        todoRepository.deleteByUuid(idToDelete);
    }
}
