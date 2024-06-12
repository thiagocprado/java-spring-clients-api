package com.luizalabs.api.clients.exception;

import com.luizalabs.api.clients.dto.DefaultErrorMessageDTO;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@Order(0)
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DefaultErrorMessageDTO.ErrorMessage>> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<DefaultErrorMessageDTO.ErrorMessage> errors;

        e.getBindingResult().getAllErrors();
        errors = new ArrayList<>();
        List<DefaultErrorMessageDTO.ErrorMessage> finalErrors = errors;
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            finalErrors.add(DefaultErrorMessageDTO.ErrorMessage.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .errorMessage(errorMessage)
                    .build());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<List<DefaultErrorMessageDTO.ErrorMessage>> notFoundErrorHandler(final NotFoundException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(Collections.singletonList(DefaultErrorMessageDTO.ErrorMessage.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .errorMessage(e.getMessage())
                        .build()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<List<DefaultErrorMessageDTO.ErrorMessage>> noResourceFoundErrorHandler(final NoResourceFoundException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(Collections.singletonList(DefaultErrorMessageDTO.ErrorMessage.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .errorMessage(e.getMessage())
                        .build()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConflictException.class)
    public ResponseEntity<List<DefaultErrorMessageDTO.ErrorMessage>> conflictErrorHandler(final ConflictException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(Collections.singletonList(DefaultErrorMessageDTO.ErrorMessage.builder()
                        .code(HttpStatus.CONFLICT.value())
                        .errorMessage(e.getMessage())
                        .build()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<List<DefaultErrorMessageDTO.ErrorMessage>> defaultExceptionHandler(final Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonList(DefaultErrorMessageDTO.ErrorMessage.builder()
                        .errorMessage(e.getMessage())
                        .build()));
    }
}