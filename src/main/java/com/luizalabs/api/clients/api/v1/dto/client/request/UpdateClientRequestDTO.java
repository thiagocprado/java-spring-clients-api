
package com.luizalabs.api.clients.api.v1.dto.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UpdateClientRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6234040074127742949L;

    @NotNull(message = "Id do cliente não pode estar em branco")
    @JsonProperty("id")
    private Integer id;

    @NotNull(message = "Nome do cliente não pode estar em branco")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "E-mail do cliente não pode estar em branco")
    @JsonProperty("email")
    private String email;
}
