package com.luizalabs.api.clients.api.v1.dto.client.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ClientResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -515705625914922160L;

    private Integer id;
    private String name;
    private String email;
}
