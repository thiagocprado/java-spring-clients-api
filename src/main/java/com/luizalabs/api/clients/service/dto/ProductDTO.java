package com.luizalabs.api.clients.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7577431134369996440L;

    private String id;
    private String brand;
    private String image;
    private String title;
    private BigDecimal price;
    private BigDecimal reviewScore;
}
