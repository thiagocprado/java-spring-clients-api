package com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
public class ClientFavoriteProductResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6465554373194612367L;

    private Integer id;
    private Integer clientId;
    private UUID productId;
}
