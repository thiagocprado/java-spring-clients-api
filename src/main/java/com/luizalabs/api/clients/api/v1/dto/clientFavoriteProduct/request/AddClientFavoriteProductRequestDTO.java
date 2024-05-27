package com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AddClientFavoriteProductRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7323196622927162989L;

    @Valid

    @NotBlank(message = "Id do cliente não pode estar em branco!")
    @NotNull(message = "Id do cliente não pode ser nulo!")
    private Integer clientId;

    @NotBlank(message = "Id do produto não pode estar em branco!")
    @NotNull(message = "Id do produto não pode ser nulo!")
    private String productId;
}
