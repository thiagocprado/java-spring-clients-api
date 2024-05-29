package com.luizalabs.api.clients.usecase.impl;

import com.luizalabs.api.clients.api.v1.dto.client.request.CreateClientRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.GetAllClientsRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.UpdateClientRequestDTO;
import com.luizalabs.api.clients.common.dto.PaginatedResultDTO;
import com.luizalabs.api.clients.common.dto.PaginationDTO;
import com.luizalabs.api.clients.common.helper.JsonHelper;
import com.luizalabs.api.clients.entity.Client;
import com.luizalabs.api.clients.exception.BadRequestException;
import com.luizalabs.api.clients.exception.ConflictException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.repository.ClientFavoriteProductRepository;
import com.luizalabs.api.clients.repository.ClientRepository;
import com.luizalabs.api.clients.usecase.ClientUseCaseInterface;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ClientUseCase implements ClientUseCaseInterface {
    private final ClientRepository clientRepository;
    private final ClientFavoriteProductRepository clientFavoriteProductRepository;
    private final JsonHelper jsonHelper;

    @Autowired
    public ClientUseCase(ClientRepository clientRepository, ClientFavoriteProductRepository clientFavoriteProductRepository, JsonHelper jsonHelper) {
        this.clientRepository = clientRepository;
        this.clientFavoriteProductRepository = clientFavoriteProductRepository;
        this.jsonHelper = jsonHelper;
    }

    @Override
    public Client createClient(@NonNull CreateClientRequestDTO requestBody) throws ConflictException {
        var clientExists = this.clientRepository.findByEmail(requestBody.getEmail());

        if (clientExists.isPresent()) {
            throw new ConflictException("Cliente já cadastrado!");
        }

        var clientJson = this.jsonHelper.convertObjectToJson(requestBody);
        var client = this.jsonHelper.convertJsonToClass(clientJson, Client.class);

        return this.clientRepository.save(client);
    }

    @Override
    public void deleteClient(@NonNull Integer id) throws NotFoundException {
        var response = this.clientRepository.findById(id);

        if (response.isEmpty()) {
            throw new NotFoundException("Cliente não encontrado!");
        }

        this.clientFavoriteProductRepository.deleteByClientId(id);
        this.clientRepository.deleteById(id);
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

        if (response.isEmpty()) {
            throw new NotFoundException("Cliente não encontrado!");
        }

        return response.get();
    }

    @Override
    public Client updateClient(@NonNull UpdateClientRequestDTO requestBody) throws ConflictException, NotFoundException {
        var clientExists = this.clientRepository.findById(requestBody.getId());

        if (clientExists.isEmpty()) {
            throw new NotFoundException("Cliente não encontrado!");
        }

        var emailExists = this.clientRepository.findByEmail(requestBody.getEmail());

        if (emailExists.isPresent() && !clientExists.get().getEmail().equals(requestBody.getEmail())) {
            throw new ConflictException("Email já cadastrado!");
        }

        clientExists.get().setName(requestBody.getName());
        clientExists.get().setEmail(requestBody.getEmail());

        return this.clientRepository.save(clientExists.get());
    }
}
