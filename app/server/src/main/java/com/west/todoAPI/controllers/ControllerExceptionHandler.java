package com.west.todoAPI.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoRestController.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormat(Exception exception) {
        LOGGER.info(exception.getLocalizedMessage());

        return "Todo ID must be an integer";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RuntimeException.class)
    public String handleIdNotFound(Exception exception) {

        LOGGER.info(exception.getLocalizedMessage());

        return "Todo not found.";
    }




}
