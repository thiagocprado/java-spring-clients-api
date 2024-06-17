package com.luizalabs.api.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ClientFavoriteProductRecordDTO(
        @JsonProperty("clientId")
        @Positive(message = "Insira um id do cliente válido!")
        @NotNull(message = "Id do cliente não pode ser nulo!")
        Integer clientId,

        @JsonProperty("productId")
        @NotBlank(message = "Id do produto não pode estar em branco!")
        @NotNull(message = "Id do produto não pode ser nulo!")
        String productId
) {
}
