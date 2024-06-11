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
public class NotFoundException extends Exception {
    private final HttpStatus statusCode;

    public NotFoundException(String message) {
        super(message);
        this.statusCode = HttpStatus.NOT_FOUND;
    }
}
