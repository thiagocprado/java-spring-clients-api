package com.luizalabs.api.clients.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private String id;
    private String brand;
    private String image;
    private String title;
    private BigDecimal price;
    private BigDecimal reviewScore;
}
