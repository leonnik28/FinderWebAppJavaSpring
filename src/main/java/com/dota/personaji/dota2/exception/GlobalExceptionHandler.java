package com.dota.personaji.dota2.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";

    @ExceptionHandler(value = {IllegalArgumentException.class, EntityNotFoundException.class})
    public ResponseEntity<Object> handleBadRequest(Exception e) {
        logger.error("Bad request: ", e);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(STATUS, HttpStatus.BAD_REQUEST);
        body.put(MESSAGE, e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNotFound(Exception e) {
        logger.error("Resource not found: ", e);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(STATUS, HttpStatus.NOT_FOUND);
        body.put(MESSAGE, e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handleServerError(Exception e) {
        logger.error("Server error: ", e);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
        body.put(MESSAGE, e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}