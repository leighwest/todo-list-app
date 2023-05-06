package com.west.todoAPI.validator;

import com.west.todoAPI.dto.request.InitialTodoRequestModel;

import java.util.Optional;

@FunctionalInterface
public interface BaseValidator {
    Optional<String> validate(InitialTodoRequestModel todoDetails);

}
