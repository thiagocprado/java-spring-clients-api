package com.luizalabs.api.clients.usecase.impl;

import com.luizalabs.api.clients.BaseTests;
import com.luizalabs.api.clients.common.helper.JsonHelper;
import com.luizalabs.api.clients.exception.BadRequestException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.repository.ClientFavoriteProductRepository;
import com.luizalabs.api.clients.repository.ClientRepository;
import com.luizalabs.api.clients.seeder.ClientSeeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.openMocks;

public class ClientUseCaseTest extends BaseTests {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientFavoriteProductRepository clientFavoriteProductRepository;

    private ClientUseCase clientUseCase;

    @BeforeEach
    void setUp() {
        openMocks(this);
        this.clientUseCase = new ClientUseCase(clientRepository, clientFavoriteProductRepository, new JsonHelper());
    }

    @Test
    void createClient() throws BadRequestException {
        var clientRepository = ClientSeeder.clientRepository();
        var clientRequest = ClientSeeder.createClientRequestDTO();

        Mockito.when(this.clientRepository.save(any())).thenReturn(clientRepository);
        var response = this.clientUseCase.createClient(clientRequest);

        assertEquals(clientRepository.getId(), response.getId());
        assertEquals(clientRepository.getName(), response.getName());
        assertEquals(clientRepository.getEmail(), response.getEmail());
    }

    @Test
    void createClientAlreadyRegistered() {
        var clientRequest = ClientSeeder.createClientRequestDTO();
        var clientRepository = ClientSeeder.clientRepository();

        Mockito.when(this.clientRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(clientRepository));

        assertThrows(BadRequestException.class, () -> this.clientUseCase.createClient(clientRequest));
    }

    @Test
    void deleteClient() throws NotFoundException {
        var clientRepository = ClientSeeder.clientRepository();

        Mockito.when(this.clientRepository.findById(anyInt())).thenReturn(Optional.ofNullable(clientRepository));

        Mockito.doNothing().when(this.clientFavoriteProductRepository).deleteByClientId(anyInt());
        Mockito.doNothing().when(this.clientRepository).deleteById(anyInt());

        this.clientUseCase.deleteClient(anyInt());

        Mockito.verify(this.clientFavoriteProductRepository, Mockito.times(1)).deleteByClientId(anyInt());
        Mockito.verify(this.clientRepository, Mockito.times(1)).deleteById(anyInt());
    }

    @Test
    void deleteClientNotFound() {
        Mockito.when(this.clientRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.clientUseCase.deleteClient(anyInt()));
    }

    @Test
    void getAllClients() {
        var clientsRepository = ClientSeeder.clientsRepository();
        var clientsRequest = ClientSeeder.getAllClientsRequestDTO();

        Mockito.when(this.clientRepository.findAll(any(PageRequest.class))).thenReturn(clientsRepository);
        var response = this.clientUseCase.getAllClients(clientsRequest);

        assertFalse(response.getResults().isEmpty());

        var firstClient = clientsRepository.get().findFirst().orElseThrow();

        assertEquals(firstClient.getId(), response.getResults().get(0).getId());
        assertEquals(firstClient.getName(), response.getResults().get(0).getName());
        assertEquals(firstClient.getEmail(), response.getResults().get(0).getEmail());
        assertEquals(clientsRepository.getTotalElements(), 1);
    }

    @Test
    void getClientById() throws NotFoundException {
        var clientRepository = ClientSeeder.clientRepository();

        Mockito.when(this.clientRepository.findById(anyInt())).thenReturn(Optional.ofNullable(clientRepository));

        var response = this.clientUseCase.getClientById(anyInt());

        assert clientRepository != null;
        assertEquals(clientRepository.getId(), response.getId());
        assertEquals(clientRepository.getName(), response.getName());
        assertEquals(clientRepository.getEmail(), response.getEmail());
    }

    @Test
    void getClientByIdNotFound() {
        Mockito.when(this.clientRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.clientUseCase.getClientById(anyInt()));
    }

    @Test
    void updateClient() throws BadRequestException, NotFoundException {
        var clientRepository = ClientSeeder.clientRepository();
        var clientRequest = ClientSeeder.updateClientRequestDTO();

        Mockito.when(this.clientRepository.findById(anyInt())).thenReturn(Optional.ofNullable(clientRepository));
        Mockito.when(this.clientRepository.save(any())).thenReturn(clientRepository);

        var response = this.clientUseCase.updateClient(clientRequest);

        assert clientRepository != null;
        assertEquals(clientRepository.getId(), response.getId());
        assertEquals(clientRepository.getName(), response.getName());
        assertEquals(clientRepository.getEmail(), response.getEmail());
    }

    @Test
    void updateClientNotFound() {
        var clientRequest = ClientSeeder.updateClientRequestDTO();

        Mockito.when(this.clientRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.clientUseCase.updateClient(clientRequest));
    }

    @Test
    void updateClientEmailAlreadyRegistered() {
        var clientRepository = ClientSeeder.clientRepository();
        var clientRepositoryAlternative = ClientSeeder.clientRepositoryAlternative();

        var clientRequest = ClientSeeder.updateClientAlternativeRequestDTO();

        Mockito.when(this.clientRepository.findById(anyInt())).thenReturn(Optional.ofNullable(clientRepository));
        Mockito.when(this.clientRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(clientRepositoryAlternative));

        assertThrows(BadRequestException.class, () -> this.clientUseCase.updateClient(clientRequest));
    }
}
