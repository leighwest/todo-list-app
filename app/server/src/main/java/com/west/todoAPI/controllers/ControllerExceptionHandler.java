package com.west.todoAPI.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoRestController.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIdNotFound(Exception exception) {

        LOGGER.info(exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getLocalizedMessage());
    }
}
