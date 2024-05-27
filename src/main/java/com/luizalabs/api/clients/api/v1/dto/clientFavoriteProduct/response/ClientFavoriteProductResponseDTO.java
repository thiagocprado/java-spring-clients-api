package com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ClientFavoriteProductResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6465554373194612367L;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("clientId")
    private Integer clientId;

    @JsonProperty("productId")
    private String productId;
}
