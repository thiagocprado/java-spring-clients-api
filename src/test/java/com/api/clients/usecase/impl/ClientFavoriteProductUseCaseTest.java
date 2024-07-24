package com.api.clients.usecase.impl;

import com.api.clients.BaseTests;
import com.api.clients.exception.ConflictException;
import com.api.clients.exception.NotFoundException;
import com.api.clients.repository.ClientFavoriteProductRepository;
import com.api.clients.seeder.ClientFavoriteProductSeeder;
import com.api.clients.service.ProductService;
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
        this.clientFavoriteProductUseCase = new ClientFavoriteProductUseCase(clientFavoriteProductRepository, productService);
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
        var clientFavoriteProductRepository = ClientFavoriteProductSeeder.clientFavoriteProductRepostiory();

        Mockito.when(this.clientFavoriteProductRepository.findByClientIdAndProductId(anyInt(), anyString())).thenReturn(Optional.ofNullable(clientFavoriteProductRepository));
        Mockito.doNothing().when(this.clientFavoriteProductRepository).deleteByClientIdAndProductId(anyInt(), anyString());

        this.clientFavoriteProductUseCase.deleteClientFavoriteProduct(1, "product-id-1");

        Mockito.verify(this.clientFavoriteProductRepository, Mockito.times(1)).deleteByClientIdAndProductId(anyInt(), anyString());
    }

    @Test
    void deleteClientFavoriteProductNotFound() {
        Mockito.when(this.clientFavoriteProductRepository.findByClientIdAndProductId(anyInt(), anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.clientFavoriteProductUseCase.deleteClientFavoriteProduct(anyInt(), anyString()));
    }

    @Test
    void getClientFavoriteProducts() throws NotFoundException {
        var clientFavoriteProductsRepository = ClientFavoriteProductSeeder.clientFavoriteProductsRepository();
        var product = ClientFavoriteProductSeeder.productRecordDTO();

        Mockito.when(this.productService.getProductDetails(anyString())).thenReturn(product);
        Mockito.when(this.clientFavoriteProductRepository.findAllByClientId(anyInt(), any(PageRequest.class))).thenReturn(clientFavoriteProductsRepository);

        var response = this.clientFavoriteProductUseCase.getClientFavoriteProducts(1, 1, 10);

        assertFalse(response.results().isEmpty());
        assertEquals(product.id(), response.results().get(0).id());
        assertEquals(clientFavoriteProductsRepository.getTotalElements(), 1);
    }

    @Test
    void getClientFavoriteProductsNotFound() {
        var clientFavoriteProductsRepository = ClientFavoriteProductSeeder.clientFavoriteProductsRepository();

        Mockito.when(this.clientFavoriteProductRepository.findAllByClientId(anyInt(), any(PageRequest.class))).thenReturn(clientFavoriteProductsRepository);
        Mockito.when(this.productService.getProductDetails(anyString())).thenAnswer(invocation -> {
            throw new NotFoundException("Produto não encontrado!");
        });

        assertThrows(NotFoundException.class, () -> this.clientFavoriteProductUseCase.getClientFavoriteProducts(1, 1, 10));
    }
}
