package com.luizalabs.api.clients.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ConflictException extends Exception {
    @Serial
    private static final long serialVersionUID = 4232856805530216765L;

    private final HttpStatus statusCode;

    public ConflictException(String message) {
        super(message);
        this.statusCode = HttpStatus.CONFLICT;
    }
}
