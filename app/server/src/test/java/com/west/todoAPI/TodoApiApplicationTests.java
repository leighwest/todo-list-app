package com.west.todoAPI;

import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.services.TodoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest

public class TodoApiApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private TodoServiceImpl todoService;

	@Test
	public void testFindAll() throws Exception {

		Todo todo = new Todo();
		todo.setId(1);
		todo.setDescription("Practice coding");
		todo.setDate(LocalDate.now());
		todo.setCompleted(false);

		List<Todo> todos = Arrays.asList(todo);
		when(todoService.getTodos()).thenReturn(todos);

		mockMvc.perform(get("/todoapi/todos/").contextPath("/todoapi")).andExpect(status().isOk());
	}

}
