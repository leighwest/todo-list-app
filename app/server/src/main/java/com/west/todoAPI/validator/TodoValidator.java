package com.west.todoAPI.validator;

import com.west.todoAPI.dto.request.InitialTodoRequestModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class TodoValidator implements BaseValidator {
    @Override
    public Optional<String> validate(InitialTodoRequestModel todoDetails) {

        if (todoDetails.getDescription().trim().isEmpty()) {
            return Optional.of("Todo description must not be empty");
        }

        if (todoDetails.getDescription().length() < 3) {
            return Optional.of("Todo description must be at least 3 characters");
        }

        if (todoDetails.getDate().isBefore(LocalDate.now())) {
            return Optional.of("Todo must not be in the past");
        }

        return Optional.empty();
    }
}
