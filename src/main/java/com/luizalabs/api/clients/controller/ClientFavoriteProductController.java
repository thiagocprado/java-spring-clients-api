package com.luizalabs.api.clients.controller;

import com.luizalabs.api.clients.dto.ClientFavoriteProductRecordDTO;
import com.luizalabs.api.clients.dto.PaginatedRecordDTO;
import com.luizalabs.api.clients.entity.ClientFavoriteProduct;
import com.luizalabs.api.clients.exception.ConflictException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.dto.ProductRecordDTO;
import com.luizalabs.api.clients.usecase.ClientFavoriteProductUseCaseInterface;
import com.luizalabs.api.clients.usecase.ClientUseCaseInterface;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/clients/products")
public class ClientFavoriteProductController {
    private final ClientFavoriteProductUseCaseInterface clientFavoriteProductUseCase;
    private final ClientUseCaseInterface clientUseCase;

    @Autowired
    public ClientFavoriteProductController(ClientFavoriteProductUseCaseInterface clientFavoriteProductUseCase, ClientUseCaseInterface clientUseCase) {
        this.clientFavoriteProductUseCase = clientFavoriteProductUseCase;
        this.clientUseCase = clientUseCase;
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<ClientFavoriteProduct> addClientFavoriteProduct(@RequestBody @Valid ClientFavoriteProductRecordDTO requestBody) throws ConflictException, NotFoundException {
        this.clientUseCase.getClientById(requestBody.clientId());

        var response = this.clientFavoriteProductUseCase.addClientFavoriteProduct(requestBody);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ResponseBody
    @DeleteMapping("/{id}/{productId}")
    public ResponseEntity<Object> deleteClientFavoriteProduct(@PathVariable Integer id, @PathVariable String productId) throws NotFoundException {
        this.clientUseCase.getClientById(id);

        this.clientFavoriteProductUseCase.deleteClientFavoriteProduct(id, productId);

        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<PaginatedRecordDTO<ProductRecordDTO>> getClientFavoriteProducts(@PathVariable Integer id, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) throws NotFoundException {
        this.clientUseCase.getClientById(id);

        var response = this.clientFavoriteProductUseCase.getClientFavoriteProducts(id, page, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
