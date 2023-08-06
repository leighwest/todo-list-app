package com.west.todoAPI.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.west.todoAPI.dto.TodoDto;
import com.west.todoAPI.dto.request.InitialTodoRequestModel;
import com.west.todoAPI.dto.request.UpdateTodoRequestModel;
import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.repositories.TodoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceImplTest {
    @Mock
    private TodoRepository todoRepository;

//    @Mock
//    private Transformer transformer;

//    @InjectMocks
//    private TodoServiceImpl todoService;

    static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void getTodos_ShouldReturnListOfTodoDtos() throws IOException{

        // Arrange
        List<TodoDto> expectedTodoDtoList = getTodoDtos();
        List<Todo> mockedTodos = getTodos();
        Transformer transformerSpy = Mockito.spy(Transformer.class);

        when(todoRepository.findAll()).thenReturn(mockedTodos);
        Mockito.doCallRealMethod().when(transformerSpy).transformToDto(Mockito.any(Todo.class));

        TodoServiceImpl todoService = new TodoServiceImpl(todoRepository, transformerSpy);

        // FIXME: this is bad...it assumes the todoTestData and todoDtoTestData JSON data will always be in order. Adding an object to either file would break this
        // This is why I'm using a spy
//        for (int i = 0; i < mockedTodos.size(); i++) {
//            when(transformer.transformToDto(mockedTodos.get(i))).thenReturn(expectedTodoDtoList.get(i));
//        }

        // Act
        todoService.getTodos();

        verify(todoRepository).findAll();
        verify(transformerSpy, times(expectedTodoDtoList.size())).transformToDto(any());
    }

    @Test
    void findByUuid_ShouldReturnOptionalEmpty_IfTodoNotFound() {

        // Arrange
        UUID uuid = UUID.randomUUID();
        when(todoRepository.findByUuid(uuid)).thenReturn(Optional.empty());
        Transformer transformerSpy = Mockito.spy(Transformer.class);

        TodoServiceImpl todoService = new TodoServiceImpl(todoRepository, transformerSpy);

        // Act
        Optional<TodoDto> todo = todoService.findByUuid(uuid);

        // Assert
        assertEquals(Optional.empty(), todo);

        // Verify
        verify(todoRepository).findByUuid(uuid);
        verifyNoMoreInteractions(todoRepository);
    }

    @Test
    void findByUuid_ShouldReturnTodoDto_IfTodoFound() throws IOException {

        // Arrange
        List<Todo> mockedTodos = getTodos();
        Todo expectedTodo = mockedTodos.get(0);
        TodoDto expectedTodoDto = getTodoDtos().get(0);

        when(todoRepository.findByUuid(any())).thenReturn(Optional.of(expectedTodo));

        Transformer transformerSpy = Mockito.spy(Transformer.class);

        TodoServiceImpl todoService = new TodoServiceImpl(todoRepository, transformerSpy);

        // Act
        Optional<TodoDto> actualOptional = todoService.findByUuid(UUID.randomUUID());

        // Assert
        assertTrue(actualOptional.isPresent());
        TodoDto actual = actualOptional.get();
        assertEquals(expectedTodoDto.getUuid(), actual.getUuid());
        assertEquals(expectedTodoDto.getDescription(), actual.getDescription());
        assertEquals(expectedTodoDto.getDate(), actual.getDate());
        assertEquals(expectedTodoDto.isCompleted(), actual.isCompleted());

        // Verify
        verify(todoRepository).findByUuid(any());
    }

    @Test
    void save_ShouldReturnTodoDto() throws IOException {

        // Arrange
        InitialTodoRequestModel initialTodo = getInitialTodoRequestModel();
        Todo savedTodo = getTodos().get(0);
        TodoDto todoDto = getTodoDtos().get(0);
        when(todoRepository.save(any())).thenReturn(savedTodo);

        Transformer transformerSpy = Mockito.spy(Transformer.class);

        TodoServiceImpl todoService = new TodoServiceImpl(todoRepository, transformerSpy);

        // Act
        TodoDto actual = todoService.save(initialTodo);

        // Assert
        assertEquals(todoDto.getDescription(), actual.getDescription());
        assertEquals(todoDto.getDate(), actual.getDate());
        assertEquals(todoDto.isCompleted(), actual.isCompleted());

        // Verify
        verify(todoRepository).save(any());
    }

    @Test
    void update_ShouldReturnTodoDto() throws IOException {

        Todo todo = getTodos().get(1);
        TodoDto todoDto = getTodoDtos().get(1);
        UpdateTodoRequestModel updatedTodo = getUpdateTodoRequestModel();

        when(todoRepository.findByUuid(any())).thenReturn(Optional.of(todo));

        when(todoRepository.save(any())).thenReturn(todo);

        Transformer transformerSpy = Mockito.spy(Transformer.class);

        TodoServiceImpl todoService = new TodoServiceImpl(todoRepository, transformerSpy);

        // Act
        TodoDto actual = todoService.update(UUID.fromString("3dd0be0b-9ac0-4510-9d4b-491c6d9fa9ee"), updatedTodo);

        // Assert
        assertEquals(todoDto.getUuid(), actual.getUuid());
        assertEquals("My second todo is updated", actual.getDescription());
        assertEquals(todoDto.getDate(), actual.getDate());
        assertEquals(todoDto.isCompleted(), actual.isCompleted());

        // Verify
        verify(todoRepository).save(any());
    }

    List<TodoDto> getTodoDtos() throws IOException{
        TypeReference<List<TodoDto>> todoDtoTypeReference = new TypeReference<>() {};
        return objectMapper.readValue(new File("src/test/resources/todoDtoTestData.json"), todoDtoTypeReference);
    }

    List<Todo> getTodos() throws IOException {

        TypeReference<List<Todo>> todoTypeReference = new TypeReference<>() {};
        return objectMapper.readValue(new File("src/test/resources/todoTestData.json"), todoTypeReference);
    }

    UpdateTodoRequestModel getUpdateTodoRequestModel() throws IOException {
        TypeReference<UpdateTodoRequestModel> updateTodoRequestModelTypeReference = new TypeReference<>() {};
        return objectMapper.readValue(new File("src/test/resources/updateTodoRequestModelTestData.json"), updateTodoRequestModelTypeReference);
    }

    InitialTodoRequestModel getInitialTodoRequestModel() throws IOException {
        TypeReference<InitialTodoRequestModel> initialTodoRequestModelTypeReference = new TypeReference<>() {};
        return objectMapper.readValue(new File("src/test/resources/initialTodoRequestModelTestData.json"), initialTodoRequestModelTypeReference);
    }
}



