package com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class GetAllClientFavoriteProductsRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3820183958764902259L;

    @Positive(message = "Insira um id do cliente válido!")
    @NotNull(message = "Id do cliente não pode ser nulo!")
    private Integer clientId;

    @Min(value = 1, message = "Página deve ser pelo menos 1!")
    @NotNull(message = "Página não pode ser nulo!")
    private Integer page;

    @Min(value = 1, message = "Tamanho da página deve ser pelo menos 1!")
    @NotNull(message = "Tamanho da página não pode ser nulo!")
    private Integer pageSize;
}
