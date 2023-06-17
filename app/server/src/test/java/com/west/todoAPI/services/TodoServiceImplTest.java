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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    Todo todo1 = new Todo(UUID.fromString("58069600-4377-463e-8ae6-6de175cea5ed"), "Todo 1", LocalDate.of(2024, 12, 26), false);
    Todo todo2 = new Todo(UUID.fromString("3dd0be0b-9ac0-4510-9d4b-491c6d9fa9ee"), "Todo 2", LocalDate.of(2026, 2, 17), false);
    TodoDto todoDto1 = EntityDtoTransformer.toDto(todo1);
    TodoDto todoDto2 = EntityDtoTransformer.toDto(todo2);

    @Test
    void getTodos_ShouldReturnListOfTodoDtos() {

        // Arrange
        List<TodoDto> expectedTodoList = new ArrayList<>();
        expectedTodoList.add(todoDto1);
        expectedTodoList.add(todoDto2);

        try (MockedStatic<EntityDtoTransformer> mockedStatic = mockStatic(EntityDtoTransformer.class)) {
            TodoDto dto1 = Mockito.mock(TodoDto.class);
            when (dto1.getUuid()).thenReturn(UUID.fromString("58069600-4377-463e-8ae6-6de175cea5ed"));
            when (dto1.getDescription()).thenReturn("Todo 1");
            when (dto1.getDate()).thenReturn(LocalDate.of(2024, 12, 26));
            when (dto1.isCompleted()).thenReturn(false);

            TodoDto dto2 = Mockito.mock(TodoDto.class);
            when (dto2.getUuid()).thenReturn(UUID.fromString("3dd0be0b-9ac0-4510-9d4b-491c6d9fa9ee"));
            when (dto2.getDescription()).thenReturn("Todo 2");
            when (dto2.getDate()).thenReturn(LocalDate.of(2026, 2, 17));
            when (dto2.isCompleted()).thenReturn(false);

            when(todoRepository.findAll()).thenReturn(List.of(todo1, todo2));

            mockedStatic.when(() -> EntityDtoTransformer.toDto(any(Todo.class)))
                    .thenReturn(dto1, dto2);

            // Act
            List<TodoDto> actual = todoService.getTodos();

            // Assert
            for (int i = 0; i < expectedTodoList.size(); i++) {
                assertEquals(expectedTodoList.get(i).getUuid(), actual.get(i).getUuid());
                assertEquals(expectedTodoList.get(i).getDescription(), actual.get(i).getDescription());
                assertEquals(expectedTodoList.get(i).getDate(), actual.get(i).getDate());
                assertEquals(expectedTodoList.get(i).isCompleted(), actual.get(i).isCompleted());
            }

//            assertIterableEquals(expectedTodoList, actual);
            verify(todoRepository).findAll();
        }
    }
    @Test
    void findByUuid_ShouldThrowException_IfTodoNotFound() {

        // Arrange
        UUID uuid = UUID.randomUUID();
        when(todoRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> todoService.findByUuid(uuid));

        verify(todoRepository).findByUuid(uuid);
        verifyNoMoreInteractions(todoRepository);
    }

    @Test
    void findByUuid_ShouldReturnTodoDto_IfTodoFound() {

        // Arrange
        when(todoRepository.findByUuid(any())).thenReturn(Optional.of(todo1));

        try (MockedStatic<EntityDtoTransformer> mockedStatic = mockStatic(EntityDtoTransformer.class)) {
            TodoDto dto1 = Mockito.mock(TodoDto.class);
            when (dto1.getUuid()).thenReturn(UUID.fromString("58069600-4377-463e-8ae6-6de175cea5ed"));
            when (dto1.getDescription()).thenReturn("Todo 1");
            when (dto1.getDate()).thenReturn(LocalDate.of(2024, 12, 26));
            when (dto1.isCompleted()).thenReturn(false);

            mockedStatic.when(() -> EntityDtoTransformer.toDto(any(Todo.class)))
                    .thenReturn(dto1);

            // Act
            TodoDto actual = todoService.findByUuid(UUID.randomUUID());

            // Assert
            assertEquals(todoDto1.getUuid(), actual.getUuid());
            assertEquals(todoDto1.getDescription(), actual.getDescription());
            assertEquals(todoDto1.getDate(), actual.getDate());
            assertEquals(todoDto1.isCompleted(), actual.isCompleted());
            }

        }
    }



