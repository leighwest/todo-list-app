package com.west.todoAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.west.todoAPI.dto.TodoDto;
import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.services.TodoService;
import com.west.todoAPI.util.EntityDtoTransformer;
import com.west.todoAPI.validator.TodoValidator;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoApiApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TodoService todoService;

    @MockBean
    private TodoValidator tv;

	@Test
	public void testFindAll() throws Exception {

		Todo todo = new Todo();
		todo.setId(1);
		todo.setDescription("Practice coding");

		TodoDto todoDto = EntityDtoTransformer.toDto(todo);

		List<TodoDto> todos = List.of(todoDto);
		when(todoService.getTodos()).thenReturn(todos);

		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();

		mockMvc.perform(get("/todoapi/todos/").contextPath("/todoapi")).andExpect(status().is2xxSuccessful())
				.andExpect(content().json(objectWriter.writeValueAsString(todos)));
	}

}
