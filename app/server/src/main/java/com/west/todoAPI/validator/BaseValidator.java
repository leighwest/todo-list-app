package com.west.todoAPI.validator;

import com.west.todoAPI.entities.Todo;

import java.util.Optional;

@FunctionalInterface
public interface BaseValidator {
    Optional<String> validate(Todo todo);

}
