package com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteClientFavoriteProductRequestDTO {
    private Integer clientId;
    private String productId;
}
