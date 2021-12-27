package com.west.todoAPI.repositories;

import com.west.todoAPI.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
