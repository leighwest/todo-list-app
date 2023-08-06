package com.west.todoAPI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.west.todoAPI.controllers.TodoRestController;
import com.west.todoAPI.dto.TodoDto;
import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.repositories.TodoRepository;
import com.west.todoAPI.services.TodoService;
import com.west.todoAPI.validator.TodoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class TodoApiApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private TodoService todoService;

    @MockBean
    private TodoValidator todoValidator;

	@MockBean
	private TodoRepository todoRepository;


	@BeforeEach
	void setUp() {
		TodoRestController todoRestController = new TodoRestController(todoService, todoValidator);
		mockMvc = MockMvcBuilders.standaloneSetup(todoRestController).build();

		objectMapper.findAndRegisterModules();
	}

	@Test
	public void testFindAll() throws Exception {

		List<TodoDto> todoDtos = getTodoDtos();
		List<Todo> todos = getTodos();

		when(todoService.getTodos()).thenReturn(todoDtos);
		when(todoRepository.findAll()).thenReturn(todos);

		ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

		mockMvc.perform(get("/todoapi/todos/").contextPath("/todoapi")).andExpect(status().is2xxSuccessful())
				.andExpect(content().json(objectWriter.writeValueAsString(todoDtos)));
	}

	List<TodoDto> getTodoDtos() throws IOException{
		TypeReference<List<TodoDto>> todoDtoTypeReference = new TypeReference<>() {};
		return objectMapper.readValue(new File("src/test/resources/todoDtoTestData.json"), todoDtoTypeReference);
	}

	List<Todo> getTodos() throws IOException {

		TypeReference<List<Todo>> todoTypeReference = new TypeReference<>() {};
		return objectMapper.readValue(new File("src/test/resources/todoTestData.json"), todoTypeReference);
	}

}
