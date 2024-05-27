package com.luizalabs.api.clients.api.v1.dto.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ClientResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -515705625914922160L;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;
}
