package com.luizalabs.api.clients.seeder;

import com.luizalabs.api.clients.api.v1.dto.client.request.CreateClientRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.GetAllClientsRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.UpdateClientRequestDTO;
import com.luizalabs.api.clients.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

public class ClientSeeder {
    // dtos
    public static CreateClientRequestDTO createClientRequestDTO() {
        return CreateClientRequestDTO.builder()
                .name("nome cliente")
                .email("nomecliente@email.com")
                .build();
    }

    public static UpdateClientRequestDTO updateClientRequestDTO() {
        return UpdateClientRequestDTO.builder()
                .id(1)
                .name("nome cliente")
                .email("nomecliente@email.com")
                .build();
    }

    public static UpdateClientRequestDTO updateClientAlternativeRequestDTO() {
        return UpdateClientRequestDTO.builder()
                .id(1)
                .name("nome cliente")
                .email("nomecliente2@email.com")
                .build();
    }

    public static GetAllClientsRequestDTO getAllClientsRequestDTO() {
        return GetAllClientsRequestDTO.builder()
                .page(1)
                .pageSize(10)
                .build();
    }

    // repositories

    public static Page<Client> clientsRepository() {
        var clients = Collections.singletonList(
                Client.builder()
                        .id(1)
                        .name("nome cliente")
                        .email("nomecliente@email.com")
                        .build()
        );

        var pageable = PageRequest.of(0, 10);

        return new PageImpl<>(clients, pageable, clients.size());
    }

    public static Client clientRepository() {
        return Client.builder()
                .id(1)
                .name("nome cliente")
                .email("nomecliente@email.com")
                .build();
    }

    public static Client clientRepositoryAlternative() {
        return Client.builder()
                .id(1)
                .name("nome cliente dois")
                .email("nomecliente2@email.com")
                .build();
    }
}
