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
public class PaginationDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8437640480718554986L;

    private Integer page;
    private Integer pageSize;
    private Integer totalElements;
}
