package com.west.todoAPI.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.west.todoAPI.dto.TodoDto;
import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.repositories.TodoRepository;
import com.west.todoAPI.services.TodoServiceImpl;
import com.west.todoAPI.validator.TodoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoRestController.class)
class TodoRestControllerTest {

    @MockBean
    private TodoServiceImpl mockTodoService;

    @MockBean
    private TodoRepository todoRepository;

    @MockBean
    private TodoValidator validator;

    @Autowired
    private MockMvc mvc;

    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void shouldReturnTodoDtosSuccessfully() throws Exception {

        List<TodoDto> todoDtos = getTodoDtos();
        List<Todo> todos = getTodos();

        when(mockTodoService.getTodos()).thenReturn(todoDtos);
        when(todoRepository.findAll()).thenReturn(todos);

        assertIterableEquals(todoDtos, mockTodoService.getTodos());

        mvc.perform(MockMvcRequestBuilders
                        .get("/todos")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                // expect that the JSON response should have a field named description in each of its elements
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].description").exists());
    }


    List<TodoDto> getTodoDtos() throws IOException {
        TypeReference<List<TodoDto>> todoDtoTypeReference = new TypeReference<>() {
        };
        return objectMapper.readValue(new File("src/test/resources/todoDtoTestData.json"), todoDtoTypeReference);
    }

    List<Todo> getTodos() throws IOException {
        TypeReference<List<Todo>> todoTypeReference = new TypeReference<>() {
        };
        return objectMapper.readValue(new File("src/test/resources/todoTestData.json"), todoTypeReference);
    }
}