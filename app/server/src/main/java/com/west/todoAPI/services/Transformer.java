package com.west.todoAPI.services;

import com.west.todoAPI.dto.TodoDto;
import com.west.todoAPI.entities.Todo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class Transformer {

    public TodoDto transformToDto(Todo todo) {
        TodoDto dto = new TodoDto();
        BeanUtils.copyProperties(todo, dto);
        return dto;
    }

    public Todo transformToEntity(TodoDto todoDto) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoDto, todo);
        return todo;
    }
}
