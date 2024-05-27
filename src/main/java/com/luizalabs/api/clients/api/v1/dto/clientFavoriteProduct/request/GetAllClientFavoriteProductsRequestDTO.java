package com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllClientFavoriteProductsRequestDTO {
    private Integer clientId;
    protected Integer page = 1;
    protected Integer pageSize = 7;
}
