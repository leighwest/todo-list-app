package com.west.todoAPI.util;

import com.west.todoAPI.dto.TodoDto;
import com.west.todoAPI.entities.Todo;
import org.springframework.beans.BeanUtils;

public class EntityDtoTransformer {

    public static TodoDto toDto(Todo todo){
        TodoDto dto = new TodoDto();
        BeanUtils.copyProperties(todo, dto);
        return dto;
    }

    public static Todo toEntity(TodoDto todoDto){
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoDto, todo);
        return todo;
    }
}
