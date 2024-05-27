package com.luizalabs.api.clients.api.v1.dto.client.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class GetAllClientsRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1500732637285959152L;

    @Min(value = 1, message = "Página deve ser pelo menos 1!")
    @NotNull(message = "Página não pode ser nulo!")
    private Integer page;

    @Min(value = 1, message = "Tamanho da página deve ser pelo menos 1!")
    @NotNull(message = "Tamanho da página não pode ser nulo!")
    private Integer pageSize;
}
