package com.luizalabs.api.clients.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorMessage implements Serializable {

        @Serial
        private static final long serialVersionUID = 482881720014522629L;

        private Integer code;
        private String errorMessage;
    }
}
