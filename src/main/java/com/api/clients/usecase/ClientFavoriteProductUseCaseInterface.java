package com.api.clients.usecase;

import com.api.clients.dto.ClientFavoriteProductRecordDTO;
import com.api.clients.dto.PaginatedRecordDTO;
import com.api.clients.entity.ClientFavoriteProduct;
import com.api.clients.exception.ConflictException;
import com.api.clients.exception.NotFoundException;
import com.api.clients.dto.ProductRecordDTO;
import org.springframework.stereotype.Service;

@Service
public interface ClientFavoriteProductUseCaseInterface {
    ClientFavoriteProduct addClientFavoriteProduct(ClientFavoriteProductRecordDTO requestBody) throws ConflictException, NotFoundException;

    void deleteClientFavoriteProduct(Integer id, String productId) throws NotFoundException;

    PaginatedRecordDTO<ProductRecordDTO> getClientFavoriteProducts(Integer id, Integer page, Integer pageSize) throws NotFoundException;
}
