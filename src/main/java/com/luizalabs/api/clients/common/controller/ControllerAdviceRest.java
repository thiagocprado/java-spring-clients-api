package com.luizalabs.api.clients.common.controller;

import com.luizalabs.api.clients.common.dto.DefaultErrorMessageDTO;
import com.luizalabs.api.clients.exception.BadRequestException;
import com.luizalabs.api.clients.exception.NotFoundException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@Order(0)
public class ControllerAdviceRest {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<List<DefaultErrorMessageDTO.ErrorMessage>> notFoundErrorHandler(final NotFoundException e) {
        final var message = MessageFormat.format("{0} not found", e.getMessage());

        return ResponseEntity.status(e.getStatusCode())
                .body(Collections.singletonList(DefaultErrorMessageDTO.ErrorMessage.builder()
                        .errorMessage(message)
                        .build()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<List<DefaultErrorMessageDTO.ErrorMessage>> badRequestErrorHandler(final NotFoundException e) {
        final var message = MessageFormat.format("{0} not found", e.getMessage());

        return ResponseEntity.status(e.getStatusCode())
                .body(Collections.singletonList(DefaultErrorMessageDTO.ErrorMessage.builder()
                        .errorMessage(message)
                        .build()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<DefaultErrorMessageDTO.ErrorMessage>> defaultExceptionHandler(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonList(DefaultErrorMessageDTO.ErrorMessage.builder()
                        .errorMessage(e.getMessage())
                        .build()));
    }
}
