package com.luizalabs.api.clients.usecase;

import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.AddClientFavoriteProductRequestDTO;
import com.luizalabs.api.clients.common.dto.PaginatedResultDTO;
import com.luizalabs.api.clients.entity.ClientFavoriteProduct;
import com.luizalabs.api.clients.exception.ConflictException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.service.dto.ProductDTO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface ClientFavoriteProductUseCaseInterface {
    ClientFavoriteProduct addClientFavoriteProduct(@NonNull AddClientFavoriteProductRequestDTO requestBody) throws ConflictException, NotFoundException;

    void deleteClientFavoriteProduct(@NonNull Integer id, @NonNull String productId) throws NotFoundException;

    PaginatedResultDTO<ProductDTO> getClientFavoriteProducts(@NonNull Integer id, @NonNull Integer page, @NonNull Integer pageSize) throws NotFoundException;
}
