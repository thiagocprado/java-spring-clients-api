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
public class NotFoundException extends Exception {
    @Serial
    private static final long serialVersionUID = 2029931755819501002L;

    private final HttpStatus statusCode;

    public NotFoundException(String message) {
        super(message);
        this.statusCode = HttpStatus.NOT_FOUND;
    }
}
