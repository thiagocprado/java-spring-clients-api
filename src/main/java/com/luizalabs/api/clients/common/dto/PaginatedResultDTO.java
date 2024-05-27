package com.luizalabs.api.clients.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatedResultDTO<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5570252523464625079L;

    private List<T> results;
    private PaginationDTO pagination;
}
