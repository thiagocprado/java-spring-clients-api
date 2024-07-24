package com.api.clients.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ConflictException extends Exception {
    private final HttpStatus statusCode;

    public ConflictException(String message) {
        super(message);
        this.statusCode = HttpStatus.CONFLICT;
    }
}
