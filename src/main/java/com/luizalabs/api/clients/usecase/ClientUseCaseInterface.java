package com.luizalabs.api.clients.usecase;

import com.luizalabs.api.clients.api.v1.dto.client.request.CreateClientRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.GetAllClientsRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.UpdateClientRequestDTO;
import com.luizalabs.api.clients.common.dto.PaginatedResultDTO;
import com.luizalabs.api.clients.entity.Client;
import com.luizalabs.api.clients.exception.BadRequestException;
import com.luizalabs.api.clients.exception.NotFoundException;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface ClientUseCaseInterface {
    Client createClient(@NonNull CreateClientRequestDTO requestBody) throws BadRequestException;

    void deleteClient(@NonNull Integer id) throws NotFoundException;

    PaginatedResultDTO<Client> getAllClients(@NonNull GetAllClientsRequestDTO requestParams);

    Client getClientById(@NonNull Integer id) throws NotFoundException;

    Client updateClient(@NonNull final UpdateClientRequestDTO client) throws BadRequestException, NotFoundException;
}
