package com.luizalabs.api.clients.api.v1.dto.client.request;

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
public class CreateClientRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8432398811426224145L;

    @Valid

    @NotNull(message = "Nome do cliente não pode estar em branco")
    @NotBlank(message = "Nome do cliente não pode estar em branco")
    private String name;

    @NotNull(message = "E-mail do cliente não pode estar em branco")
    @NotBlank(message = "E-mail do cliente não pode estar em branco")
    private String email;
}
