package com.west.todoAPI.repositories;

import com.west.todoAPI.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<Todo, UUID> {

    Optional<Todo> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

}
