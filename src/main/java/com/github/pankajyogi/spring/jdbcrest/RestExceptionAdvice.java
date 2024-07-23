package com.github.pankajyogi.spring.jdbcrest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleRuntimeException() {
        return ResponseEntity.badRequest().build();
    }
}
