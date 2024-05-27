package com.luizalabs.api.clients.usecase;

import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.AddClientFavoriteProductRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.DeleteClientFavoriteProductRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.GetAllClientFavoriteProductsRequestDTO;
import com.luizalabs.api.clients.common.dto.PaginatedResultDTO;
import com.luizalabs.api.clients.entity.ClientFavoriteProduct;
import com.luizalabs.api.clients.exception.BadRequestException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.service.dto.ProductDTO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface ClientFavoriteProductUseCaseInterface {
    ClientFavoriteProduct addClientFavoriteProduct(@NonNull AddClientFavoriteProductRequestDTO requestBody) throws BadRequestException, NotFoundException;

    void deleteClientFavoriteProduct(@NonNull DeleteClientFavoriteProductRequestDTO requestParams) throws NotFoundException;

    PaginatedResultDTO<ProductDTO> getClientFavoriteProducts(@NonNull GetAllClientFavoriteProductsRequestDTO requestParams) throws NotFoundException;
}
