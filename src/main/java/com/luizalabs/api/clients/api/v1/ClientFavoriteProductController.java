package com.luizalabs.api.clients.api.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.AddClientFavoriteProductRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.DeleteClientFavoriteProductRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.GetAllClientFavoriteProductsRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.response.ClientFavoriteProductResponseDTO;
import com.luizalabs.api.clients.common.controller.BaseController;
import com.luizalabs.api.clients.common.dto.DefaultResponseDTO;
import com.luizalabs.api.clients.exception.BadRequestException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.usecase.ClientFavoriteProductUseCaseInterface;
import com.luizalabs.api.clients.usecase.ClientUseCaseInterface;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client-product")
public class ClientFavoriteProductController extends BaseController {
    private final ClientFavoriteProductUseCaseInterface clientFavoriteProductUseCase;
    private final ClientUseCaseInterface clientUseCase;

    @Autowired
    public ClientFavoriteProductController(ClientFavoriteProductUseCaseInterface clientFavoriteProductUseCase, ClientUseCaseInterface clientUseCase) {
        this.clientFavoriteProductUseCase = clientFavoriteProductUseCase;
        this.clientUseCase = clientUseCase;
    }

    @ResponseBody
    @PostMapping("/add")
    public ResponseEntity<DefaultResponseDTO> addClientFavoriteProduct(@Valid @RequestBody AddClientFavoriteProductRequestDTO requestBody) throws BadRequestException, JsonProcessingException, NotFoundException {
        this.clientUseCase.getClientById(requestBody.getClientId());

        var response = this.clientFavoriteProductUseCase.addClientFavoriteProduct(requestBody);

        return this.buildResponse(HttpStatus.CREATED, this.deserializeToModel(response, ClientFavoriteProductResponseDTO.class));
    }

    @ResponseBody
    @DeleteMapping("/delete")
    public ResponseEntity<DefaultResponseDTO> deleteClientFavoriteProduct(@Valid DeleteClientFavoriteProductRequestDTO requestParams) throws NotFoundException {
        this.clientUseCase.getClientById(requestParams.getClientId());

        this.clientFavoriteProductUseCase.deleteClientFavoriteProduct(requestParams);

        return this.buildResponse(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @GetMapping("/list")
    public ResponseEntity<DefaultResponseDTO> getClientFavoriteProducts(@Valid GetAllClientFavoriteProductsRequestDTO requestParams) throws NotFoundException {
        this.clientUseCase.getClientById(requestParams.getClientId());

        var response = this.clientFavoriteProductUseCase.getClientFavoriteProducts(requestParams);

        return this.buildResponse(HttpStatus.OK, response.getResults(), response.getPagination());
    }
}
