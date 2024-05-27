package com.luizalabs.api.clients.api.v1.dto.client.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class DeleteClientRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6772483998573364499L;

    @Positive(message = "Insira um id válido!")
    @NotNull(message = "Id não pode ser nulo!")
    private Integer id;
}
