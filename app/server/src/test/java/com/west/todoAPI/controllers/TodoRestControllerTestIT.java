package com.west.todoAPI.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.west.todoAPI.dto.TodoDto;
import com.west.todoAPI.services.TodoServiceImpl;
import com.west.todoAPI.validator.TodoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class TodoRestControllerTestIT {

    private MockMvc mockMvc;

    @Mock
    private TodoServiceImpl todoService;

    @Mock
    private TodoValidator todoValidator;

    ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        TodoRestController todoRestController = new TodoRestController(todoService, todoValidator);
        mockMvc = MockMvcBuilders.standaloneSetup(todoRestController).build();

        objectMapper.findAndRegisterModules();

    }

    @Test
    void getTodos() throws Exception {

        // Arrange
        List<TodoDto> expectedTodoList = getTodoDtos();
        TodoDto todoDto1 = expectedTodoList.get(0);
        TodoDto todoDto2 = expectedTodoList.get(1);

        when(todoService.getTodos()).thenReturn(expectedTodoList);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].uuid").value(todoDto1.getUuid().toString()))
                .andExpect(jsonPath("$[0].description").value(todoDto1.getDescription()))
                .andExpect(jsonPath("$[0].date").value(todoDto1.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                .andExpect(jsonPath("$[0].completed").value(todoDto1.isCompleted()))
                .andExpect(jsonPath("$[1].uuid").value(todoDto2.getUuid().toString()))
                .andExpect(jsonPath("$[1].description").value(todoDto2.getDescription()))
                .andExpect(jsonPath("$[1].date").value(todoDto2.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                .andExpect(jsonPath("$[1].completed").value(todoDto2.isCompleted()))
                .andReturn();
    }

    List<TodoDto> getTodoDtos() throws IOException{
        TypeReference<List<TodoDto>> todoDtoTypeReference = new TypeReference<>() {};
        return objectMapper.readValue(new File("src/test/resources/todoDtoTestData.json"), todoDtoTypeReference);
    }
}