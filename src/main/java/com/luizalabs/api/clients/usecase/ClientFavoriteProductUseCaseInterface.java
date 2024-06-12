package com.luizalabs.api.clients.usecase;

import com.luizalabs.api.clients.dto.ClientFavoriteProductRecordDTO;
import com.luizalabs.api.clients.dto.PaginatedRecordDTO;
import com.luizalabs.api.clients.entity.ClientFavoriteProduct;
import com.luizalabs.api.clients.exception.ConflictException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.dto.ProductRecordDTO;
import org.springframework.stereotype.Service;

@Service
public interface ClientFavoriteProductUseCaseInterface {
    ClientFavoriteProduct addClientFavoriteProduct(ClientFavoriteProductRecordDTO requestBody) throws ConflictException, NotFoundException;

    void deleteClientFavoriteProduct(Integer id, String productId) throws NotFoundException;

    PaginatedRecordDTO<ProductRecordDTO> getClientFavoriteProducts(Integer id, Integer page, Integer pageSize) throws NotFoundException;
}
