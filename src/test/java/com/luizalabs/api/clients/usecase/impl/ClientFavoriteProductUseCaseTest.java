package com.luizalabs.api.clients.usecase.impl;

import com.luizalabs.api.clients.BaseTests;
import com.luizalabs.api.clients.common.helper.JsonHelper;
import com.luizalabs.api.clients.exception.BadRequestException;
import com.luizalabs.api.clients.exception.ConflictException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.repository.ClientFavoriteProductRepository;
import com.luizalabs.api.clients.seeder.ClientFavoriteProductSeeder;
import com.luizalabs.api.clients.service.ProductService;
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

public class ClientFavoriteProductUseCaseTest extends BaseTests {
    @Mock
    private ClientFavoriteProductRepository clientFavoriteProductRepository;

    @Mock
    private ProductService productService;

    private ClientFavoriteProductUseCase clientFavoriteProductUseCase;

    @BeforeEach
    void setUp() {
        openMocks(this);
        this.clientFavoriteProductUseCase = new ClientFavoriteProductUseCase(clientFavoriteProductRepository, productService, new JsonHelper());
    }

    @Test
    void addClientFavoriteProduct() throws ConflictException, NotFoundException {
        var clientFavoriteProductRepository = ClientFavoriteProductSeeder.clientFavoriteProductRepostiory();
        var clientFavoriteProductRequest = ClientFavoriteProductSeeder.addClientFavoriteProductRequestDTO();

        Mockito.when(this.clientFavoriteProductRepository.save(any())).thenReturn(clientFavoriteProductRepository);
        var response = this.clientFavoriteProductUseCase.addClientFavoriteProduct(clientFavoriteProductRequest);

        assertEquals(clientFavoriteProductRepository.getId(), response.getId());
        assertEquals(clientFavoriteProductRepository.getClientId(), response.getClientId());
        assertEquals(clientFavoriteProductRepository.getProductId(), response.getProductId());
    }

    @Test
    void addClientFavoriteProductAlreadyRegistered() {
        var clientFavoriteProductRepository = ClientFavoriteProductSeeder.clientFavoriteProductRepostiory();
        var clientFavoriteProductRequest = ClientFavoriteProductSeeder.addClientFavoriteProductRequestDTO();

        Mockito.when(this.clientFavoriteProductRepository.findByClientIdAndProductId(anyInt(), anyString())).thenReturn(Optional.ofNullable(clientFavoriteProductRepository));

        assertThrows(ConflictException.class, () -> this.clientFavoriteProductUseCase.addClientFavoriteProduct(clientFavoriteProductRequest));
    }

    @Test
    void deleteClientFavoriteProduct() throws NotFoundException {
        var clientFavoriteProductRequest = ClientFavoriteProductSeeder.deleteClientFavoriteProductRequestDTO();
        var clientFavoriteProductRepository = ClientFavoriteProductSeeder.clientFavoriteProductRepostiory();

        Mockito.when(this.clientFavoriteProductRepository.findByClientIdAndProductId(anyInt(), anyString())).thenReturn(Optional.ofNullable(clientFavoriteProductRepository));
        Mockito.doNothing().when(this.clientFavoriteProductRepository).deleteByClientIdAndProductId(anyInt(), anyString());

        this.clientFavoriteProductUseCase.deleteClientFavoriteProduct(clientFavoriteProductRequest);

        Mockito.verify(this.clientFavoriteProductRepository, Mockito.times(1)).deleteByClientIdAndProductId(anyInt(), anyString());
    }

    @Test
    void deleteClientFavoriteProductNotFound() {
        var clientFavoriteProductRequest = ClientFavoriteProductSeeder.deleteClientFavoriteProductRequestDTO();

        Mockito.when(this.clientFavoriteProductRepository.findByClientIdAndProductId(anyInt(), anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.clientFavoriteProductUseCase.deleteClientFavoriteProduct(clientFavoriteProductRequest));
    }

    @Test
    void getClientFavoriteProducts() throws NotFoundException {
        var clientFavoriteProductsRepository = ClientFavoriteProductSeeder.clientFavoriteProductsRepository();
        var clientFavoriteProductsRequest = ClientFavoriteProductSeeder.getAllClientFavoriteProductsRequestDTO();
        var product = ClientFavoriteProductSeeder.productDTO();

        Mockito.when(this.productService.getProductDetails(anyString())).thenReturn(product);
        Mockito.when(this.clientFavoriteProductRepository.findAllByClientId(anyInt(), any(PageRequest.class))).thenReturn(clientFavoriteProductsRepository);

        var response = this.clientFavoriteProductUseCase.getClientFavoriteProducts(clientFavoriteProductsRequest);

        assertFalse(response.getResults().isEmpty());
        assertEquals(product.getId(), response.getResults().get(0).getId());
        assertEquals(clientFavoriteProductsRepository.getTotalElements(), 1);
    }
}
