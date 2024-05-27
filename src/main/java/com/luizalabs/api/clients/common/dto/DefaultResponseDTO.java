package com.luizalabs.api.clients.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultResponseDTO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -3494564481277858502L;

    private DetailApplicationResponse appInfo;
    private T results;
    private PaginationDTO pagination;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DetailApplicationResponse implements Serializable {
        @Serial
        private static final long serialVersionUID = 4490519029238516768L;

        private String version;
        private String name;
        private String env;
        private String description;
    }
}
