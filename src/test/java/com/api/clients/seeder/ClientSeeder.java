package com.api.clients.seeder;

import com.api.clients.dto.ClientRecordDTO;
import com.api.clients.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

public class ClientSeeder {
    // dtos
    public static ClientRecordDTO createClientRequestDTO() {
        return ClientRecordDTO.builder()
                .name("nome cliente")
                .email("nomecliente@email.com")
                .build();
    }

    public static ClientRecordDTO updateClientRequestDTO() {
        return ClientRecordDTO.builder()
                .name("nome cliente")
                .email("nomecliente@email.com")
                .build();
    }

    public static ClientRecordDTO updateClientAlternativeRequestDTO() {
        return ClientRecordDTO.builder()
                .name("nome cliente")
                .email("nomecliente2@email.com")
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
