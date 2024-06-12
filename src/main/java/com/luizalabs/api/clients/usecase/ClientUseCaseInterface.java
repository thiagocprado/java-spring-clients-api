package com.luizalabs.api.clients.usecase;

import com.luizalabs.api.clients.dto.ClientRecordDTO;
import com.luizalabs.api.clients.dto.PaginatedRecordDTO;
import com.luizalabs.api.clients.entity.Client;
import com.luizalabs.api.clients.exception.ConflictException;
import com.luizalabs.api.clients.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface ClientUseCaseInterface {
    Client createClient(ClientRecordDTO requestBody) throws ConflictException;

    void deleteClient(Integer id) throws NotFoundException;

    PaginatedRecordDTO<Client> getAllClients(Integer page, Integer pageSize);

    Client getClientById(Integer id) throws NotFoundException;

    Client updateClient(Integer id, ClientRecordDTO requestBody) throws ConflictException, NotFoundException;
}
