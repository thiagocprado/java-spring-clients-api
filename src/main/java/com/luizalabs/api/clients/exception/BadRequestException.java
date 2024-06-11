package com.luizalabs.api.clients.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class BadRequestException extends Exception {
    private final HttpStatus statusCode;

    public BadRequestException(String message) {
        super(message);
        this.statusCode = HttpStatus.BAD_REQUEST;
    }
}
