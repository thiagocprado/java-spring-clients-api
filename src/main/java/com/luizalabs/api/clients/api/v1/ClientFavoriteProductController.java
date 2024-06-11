package com.luizalabs.api.clients.api.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.AddClientFavoriteProductRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.response.ClientFavoriteProductResponseDTO;
import com.luizalabs.api.clients.common.controller.BaseController;
import com.luizalabs.api.clients.common.dto.DefaultResponseDTO;
import com.luizalabs.api.clients.exception.ConflictException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.usecase.ClientFavoriteProductUseCaseInterface;
import com.luizalabs.api.clients.usecase.ClientUseCaseInterface;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients/products")
public class ClientFavoriteProductController extends BaseController {
    private final ClientFavoriteProductUseCaseInterface clientFavoriteProductUseCase;
    private final ClientUseCaseInterface clientUseCase;

    @Autowired
    public ClientFavoriteProductController(ClientFavoriteProductUseCaseInterface clientFavoriteProductUseCase, ClientUseCaseInterface clientUseCase) {
        this.clientFavoriteProductUseCase = clientFavoriteProductUseCase;
        this.clientUseCase = clientUseCase;
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<DefaultResponseDTO> addClientFavoriteProduct(@Valid @RequestBody AddClientFavoriteProductRequestDTO requestBody) throws ConflictException, JsonProcessingException, NotFoundException {
        this.clientUseCase.getClientById(requestBody.getClientId());

        var response = this.clientFavoriteProductUseCase.addClientFavoriteProduct(requestBody);

        return this.buildResponse(HttpStatus.CREATED, this.deserializeToModel(response, ClientFavoriteProductResponseDTO.class));
    }

    @ResponseBody
    @DeleteMapping("/{id}/{productId}")
    public ResponseEntity<DefaultResponseDTO> deleteClientFavoriteProduct(@PathVariable Integer id, @PathVariable String productId) throws NotFoundException {
        this.clientUseCase.getClientById(id);

        this.clientFavoriteProductUseCase.deleteClientFavoriteProduct(id, productId);

        return this.buildResponse(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @GetMapping("/{id}/")
    public ResponseEntity<DefaultResponseDTO> getClientFavoriteProducts(@PathVariable Integer id, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) throws NotFoundException {
        this.clientUseCase.getClientById(id);

        var response = this.clientFavoriteProductUseCase.getClientFavoriteProducts(id, page, pageSize);

        return this.buildResponse(HttpStatus.OK, response.getResults(), response.getPagination());
    }
}
