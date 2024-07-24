package com.api.clients.usecase;

import com.api.clients.dto.ClientRecordDTO;
import com.api.clients.dto.PaginatedRecordDTO;
import com.api.clients.entity.Client;
import com.api.clients.exception.ConflictException;
import com.api.clients.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface ClientUseCaseInterface {
    Client createClient(ClientRecordDTO requestBody) throws ConflictException;

    void deleteClient(Integer id) throws NotFoundException;

    PaginatedRecordDTO<Client> getAllClients(Integer page, Integer pageSize);

    Client getClientById(Integer id) throws NotFoundException;

    Client updateClient(Integer id, ClientRecordDTO requestBody) throws ConflictException, NotFoundException;
}
