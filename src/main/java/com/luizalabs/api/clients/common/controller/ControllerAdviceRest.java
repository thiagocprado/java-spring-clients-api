package com.luizalabs.api.clients.common.controller;

import com.luizalabs.api.clients.common.dto.DefaultErrorMessageDTO;
import com.luizalabs.api.clients.exception.BadRequestException;
import com.luizalabs.api.clients.exception.NotFoundException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@Order(0)
public class ControllerAdviceRest {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<List<DefaultErrorMessageDTO.ErrorMessage>> notFoundErrorHandler(final NotFoundException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(Collections.singletonList(DefaultErrorMessageDTO.ErrorMessage.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .errorMessage(e.getMessage())
                        .build()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<List<DefaultErrorMessageDTO.ErrorMessage>> badRequestErrorHandler(final BadRequestException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(Collections.singletonList(DefaultErrorMessageDTO.ErrorMessage.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .errorMessage(e.getMessage())
                        .build()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<DefaultErrorMessageDTO.ErrorMessage>> defaultExceptionHandler(final Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonList(DefaultErrorMessageDTO.ErrorMessage.builder()
                        .errorMessage(e.getMessage())
                        .build()));
    }
}