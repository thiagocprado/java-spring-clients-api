package com.api.clients.usecase.impl;

import com.api.clients.dto.ClientRecordDTO;
import com.api.clients.dto.PaginatedRecordDTO;
import com.api.clients.dto.PaginationRecordDTO;
import com.api.clients.entity.Client;
import com.api.clients.exception.ConflictException;
import com.api.clients.exception.NotFoundException;
import com.api.clients.repository.ClientFavoriteProductRepository;
import com.api.clients.repository.ClientRepository;
import com.api.clients.usecase.ClientUseCaseInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ClientUseCase implements ClientUseCaseInterface {
    private final ClientRepository clientRepository;
    private final ClientFavoriteProductRepository clientFavoriteProductRepository;

    @Autowired
    public ClientUseCase(ClientRepository clientRepository, ClientFavoriteProductRepository clientFavoriteProductRepository) {
        this.clientRepository = clientRepository;
        this.clientFavoriteProductRepository = clientFavoriteProductRepository;
    }

    @Override
    public Client createClient(ClientRecordDTO requestBody) throws ConflictException {
        var clientExists = this.clientRepository.findByEmail(requestBody.email());

        if (clientExists.isPresent()) {
            throw new ConflictException("Cliente já cadastrado!");
        }

        var client = new Client();
        BeanUtils.copyProperties(requestBody, client);

        return this.clientRepository.save(client);
    }

    @Override
    public void deleteClient(Integer id) throws NotFoundException {
        var response = this.clientRepository.findById(id);

        if (response.isEmpty()) {
            throw new NotFoundException("Cliente não encontrado!");
        }

        this.clientFavoriteProductRepository.deleteByClientId(id);
        this.clientRepository.deleteById(id);
    }

    @Override
    public PaginatedRecordDTO<Client> getAllClients(Integer page, Integer pageSize) {
        var response = this.clientRepository.findAll(PageRequest.of((page - 1), pageSize));

        var pagination = PaginationRecordDTO.builder()
                .page(response.getPageable().getPageNumber() + 1)
                .pageSize(response.getPageable().getPageSize())
                .totalElements(response.getTotalElements())
                .build();

        return new PaginatedRecordDTO<>(pagination, response.toList());
    }

    @Override
    public Client getClientById(Integer id) throws NotFoundException {
        var response = this.clientRepository.findById(id);

        if (response.isEmpty()) {
            throw new NotFoundException("Cliente não encontrado!");
        }

        return response.get();
    }

    @Override
    public Client updateClient(Integer id, ClientRecordDTO requestBody) throws ConflictException, NotFoundException {
        var clientExists = this.clientRepository.findById(id);

        if (clientExists.isEmpty()) {
            throw new NotFoundException("Cliente não encontrado!");
        }

        var emailExists = this.clientRepository.findByEmail(requestBody.email());

        if (emailExists.isPresent() && !clientExists.get().getEmail().equals(requestBody.email())) {
            throw new ConflictException("Email já cadastrado!");
        }

        clientExists.get().setName(requestBody.name());
        clientExists.get().setEmail(requestBody.email());

        return this.clientRepository.save(clientExists.get());
    }
}
