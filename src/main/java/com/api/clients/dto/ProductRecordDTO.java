package com.api.clients.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductRecordDTO(
        String id,
        String brand,
        String image,
        String title,
        BigDecimal price,
        BigDecimal reviewScore
) {
}