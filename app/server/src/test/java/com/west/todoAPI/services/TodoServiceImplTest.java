package com.west.todoAPI.services;

import com.west.todoAPI.dto.TodoDto;
import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.repositories.TodoRepository;
import com.west.todoAPI.util.EntityDtoTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    @Test
    void testGetTodos() {

        // Arrange
        Todo todo1 = new Todo(UUID.fromString("58069600-4377-463e-8ae6-6de175cea5ed"), "Todo 1", LocalDate.of(2024, 12, 26), false);
        Todo todo2 = new Todo(UUID.fromString("3dd0be0b-9ac0-4510-9d4b-491c6d9fa9ee"), "Todo 2", LocalDate.of(2026, 2, 17), false);

        TodoDto todoDto1 = EntityDtoTransformer.toDto(todo1);
        TodoDto todoDto2 = EntityDtoTransformer.toDto(todo2);

        List<TodoDto> expectedTodoList = new ArrayList<>();
        expectedTodoList.add(todoDto1);
        expectedTodoList.add(todoDto2);

        try (MockedStatic<EntityDtoTransformer> mockedStatic = mockStatic(EntityDtoTransformer.class)) {
            TodoDto dto1 = Mockito.mock(TodoDto.class);
            when (dto1.getUuid()).thenReturn(UUID.fromString("58069600-4377-463e-8ae6-6de175cea5ed"));
            when (dto1.getDescription()).thenReturn("Todo 1");

            TodoDto dto2 = Mockito.mock(TodoDto.class);
            when (dto2.getUuid()).thenReturn(UUID.fromString("3dd0be0b-9ac0-4510-9d4b-491c6d9fa9ee"));
            when (dto2.getDescription()).thenReturn("Todo 2");


            when(todoRepository.findAll()).thenReturn(List.of(todo1, todo2));

            mockedStatic.when(() -> EntityDtoTransformer.toDto(any(Todo.class)))
                    .thenReturn(dto1, dto2);

            // Act
            List<TodoDto> actual = todoService.getTodos();

            // Assert
            for (int i = 0; i < expectedTodoList.size(); i++) {
                assertEquals(expectedTodoList.get(i).getUuid(), actual.get(i).getUuid());
                assertEquals(expectedTodoList.get(i).getDescription(), actual.get(i).getDescription());

            }

            verify(todoRepository).findAll();
        }
    }
}


