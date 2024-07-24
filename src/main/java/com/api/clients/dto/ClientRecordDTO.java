package com.api.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;


@Builder
public record ClientRecordDTO(
        @JsonProperty("name")
        @Pattern(regexp = "^$|^[a-zA-ZÀ-ú'\\- ]+$", message = "Nome não pode conter caracteres numéricos!")
        @NotBlank(message = "Nome do cliente não pode estar em branco!")
        @NotNull(message = "Nome do cliente não pode ser nulo!")
        String name,

        @JsonProperty("email")
        @NotBlank(message = "E-mail do cliente não pode estar em branco!")
        @NotNull(message = "E-mail do cliente não pode ser nulo!")
        @Email(message = "E-mail inválido!")
        String email
) {
}
