package com.luizalabs.api.clients.api.v1.dto.client.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class CreateClientRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8432398811426224145L;

    @Pattern(regexp = "^[a-zA-ZÀ-ú'\\- ]+$", message = "Nome não pode conter caracteres numéricos!")
    @NotBlank(message = "Nome do cliente não pode estar em branco!")
    @NotNull(message = "Nome do cliente não pode ser nulo!")
    private String name;

    @NotBlank(message = "E-mail do cliente não pode estar em branco!")
    @NotNull(message = "E-mail do cliente não pode ser nulo!")
    @Email(message = "E-mail inválido!")
    private String email;
}
