package com.west.todoAPI.services;

import com.west.todoAPI.dto.TodoDto;
import com.west.todoAPI.dto.request.InitialTodoRequestModel;
import com.west.todoAPI.dto.request.UpdateTodoRequestModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoService {

    List<TodoDto> getTodos();

    Optional<TodoDto> findByUuid(UUID uuid);

    TodoDto save(InitialTodoRequestModel initialTodo);

    TodoDto update(UUID uuid, UpdateTodoRequestModel todo);

    void deleteByUuid(UUID idToDelete);
}
