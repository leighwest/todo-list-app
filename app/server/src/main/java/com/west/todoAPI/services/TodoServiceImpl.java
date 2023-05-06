package com.west.todoAPI.services;

import com.west.todoAPI.dto.TodoDto;
import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.dto.request.InitialTodoRequestModel;
import com.west.todoAPI.dto.request.UpdateTodoRequestModel;
import com.west.todoAPI.repositories.TodoRepository;
import com.west.todoAPI.util.EntityDtoTransformer;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoServiceImpl.class);

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<TodoDto> getTodos() {

        List<TodoDto> todoList = new ArrayList<>();
        todoRepository.findAll().stream().map(EntityDtoTransformer::toDto).iterator().forEachRemaining(todoList::add);
        return todoList;
    }

    public Todo findByUuid(UUID uuid) {
        Optional<Todo> todoOptional = todoRepository.findByUuid(uuid);

        if (!todoOptional.isPresent()) {
            throw new RuntimeException("Todo not found!");
        }
        return todoOptional.get();
    }

    @Override
    public TodoDto save(InitialTodoRequestModel initialTodo) {
        Todo todo = new Todo(UUID.randomUUID(), initialTodo.getDescription(), initialTodo.getDate(), false);

        Todo savedTodo = todoRepository.save(todo);
        LOGGER.info("Saving todo with ID: " + savedTodo.getId());
        return EntityDtoTransformer.toDto(todo);
    }

    @Override
    public TodoDto update(UUID uuid, UpdateTodoRequestModel todo) {
        Todo storedTodo = findByUuid(uuid);
        storedTodo.setDescription(todo.getDescription());
        storedTodo.setCompleted(todo.getCompleted());
        storedTodo.setDate(todo.getDate());

        todoRepository.save(storedTodo);
        LOGGER.info("Updating todo with ID: " + storedTodo.getId());

        return EntityDtoTransformer.toDto(storedTodo);
    }

    public void deleteByUuid(UUID idToDelete) {
        todoRepository.deleteByUuid(idToDelete);
    }
}
