package com.luizalabs.api.clients.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultErrorMessageDTO<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -2754997796826937592L;

    private ErrorMessage error;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ErrorMessage implements Serializable {

        @Serial
        private static final long serialVersionUID = 482881720014522629L;

        private String errorMessage;
    }
}
