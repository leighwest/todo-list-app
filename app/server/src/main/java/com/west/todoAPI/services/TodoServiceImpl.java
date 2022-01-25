package com.west.todoAPI.services;

import com.west.todoAPI.controllers.TodoRestController;
import com.west.todoAPI.entities.Todo;
import com.west.todoAPI.exceptions.NotFoundException;
import com.west.todoAPI.repositories.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoServiceImpl.class);


    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getTodos() {

        List<Todo> todoList = new ArrayList<>();
        todoRepository.findAll().iterator().forEachRemaining(todoList::add);
        return todoList;
    }

    @Override
    public Todo findById(Long l) {
        Optional<Todo> todoOptional = todoRepository.findById(l);
        LOGGER.info("In the findById function, todoOptional is: " + todoOptional);


        if (!todoOptional.isPresent()) {
            System.out.println("todoOptional is not present");
            throw new NotFoundException("Todo not found!");
        }

        return todoOptional.get();
    }

    @Override
    public Todo save(Todo todo) {
        return null;
    }

    @Override
    public void deleteById(Long idToDelete) {

    }
}
