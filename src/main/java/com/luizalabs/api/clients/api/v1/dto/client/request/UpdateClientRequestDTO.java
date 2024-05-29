
package com.luizalabs.api.clients.api.v1.dto.client.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class UpdateClientRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6234040074127742949L;

    @Positive(message = "Insira um id válido!")
    @NotNull(message = "Id não pode ser nulo!")
    private Integer id;

    @Pattern(regexp = "^[a-zA-ZÀ-ú'\\- ]+$", message = "Nome não pode conter caracteres numéricos!")
    @NotBlank(message = "Nome do cliente não pode estar em branco!")
    @NotNull(message = "Nome do cliente não pode ser nulo!")
    private String name;

    @NotBlank(message = "E-mail do cliente não pode estar em branco!")
    @NotNull(message = "E-mail do cliente não pode ser nulo!")
    @Email(message = "E-mail inválido!")
    private String email;
}
