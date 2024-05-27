package com.luizalabs.api.clients.usecase.impl;

import com.luizalabs.api.clients.api.v1.dto.client.request.CreateClientRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.GetAllClientsRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.UpdateClientRequestDTO;
import com.luizalabs.api.clients.common.dto.PaginatedResultDTO;
import com.luizalabs.api.clients.common.dto.PaginationDTO;
import com.luizalabs.api.clients.entity.Client;
import com.luizalabs.api.clients.exception.BadRequestException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.repository.ClientRepository;
import com.luizalabs.api.clients.usecase.ClientUseCaseInterface;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ClientUseCase implements ClientUseCaseInterface {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(@NonNull CreateClientRequestDTO requestBody) throws BadRequestException {
        var clientExists = this.clientRepository.findByEmail(requestBody.getEmail());

        if (clientExists.isPresent()) {
            throw new BadRequestException("Cliente já cadastrado!");
        }

        var client = new Client();
        client.setName(requestBody.getName());
        client.setEmail(requestBody.getEmail());

        return this.clientRepository.save(client);
    }

    @Override
    public PaginatedResultDTO<Client> getAllClients(@NonNull GetAllClientsRequestDTO requestParams) {
        var response = this.clientRepository.findAll(PageRequest.of((requestParams.getPage() - 1), requestParams.getPageSize()));

        var pagination = new PaginationDTO(
                response.getPageable().getPageNumber() + 1,
                response.getPageable().getPageSize(),
                response.isEmpty() ? 0 : (int) response.getTotalElements()
        );

        return new PaginatedResultDTO<>(response.toList(), pagination);
    }

    @Override
    public Client getClientById(@NonNull Integer id) throws NotFoundException {
        var response = this.clientRepository.findById(id);

        if (!response.isPresent()) {
            throw new NotFoundException("Cliente não encontrado!");
        }

        return response.get();
    }

    @Override
    public Client updateClient(@NonNull UpdateClientRequestDTO requestBody) throws BadRequestException, NotFoundException {
        var clientExists = this.clientRepository.findById(requestBody.getId());

        if (!clientExists.isPresent()) {
            throw new NotFoundException("Cliente não encontrado!");
        }

        var emailExists = this.clientRepository.findByEmail(requestBody.getEmail());

        if (emailExists.isPresent() && !clientExists.get().getEmail().equals(requestBody.getEmail())) {
            throw new BadRequestException("Email já cadastrado!");
        }

        clientExists.get().setName(requestBody.getName());
        clientExists.get().setEmail(requestBody.getEmail());

        return this.clientRepository.save(clientExists.get());
    }
}
