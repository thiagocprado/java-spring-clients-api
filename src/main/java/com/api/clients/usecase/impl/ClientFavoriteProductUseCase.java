package com.api.clients.usecase.impl;

import com.api.clients.dto.ClientFavoriteProductRecordDTO;
import com.api.clients.dto.PaginatedRecordDTO;
import com.api.clients.dto.PaginationRecordDTO;
import com.api.clients.entity.ClientFavoriteProduct;
import com.api.clients.exception.ConflictException;
import com.api.clients.exception.NotFoundException;
import com.api.clients.repository.ClientFavoriteProductRepository;
import com.api.clients.service.ProductService;
import com.api.clients.dto.ProductRecordDTO;
import com.api.clients.usecase.ClientFavoriteProductUseCaseInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Transactional
public class ClientFavoriteProductUseCase implements ClientFavoriteProductUseCaseInterface {
    private final ClientFavoriteProductRepository clientFavoriteProductRepository;
    private final ProductService productService;

    public ClientFavoriteProductUseCase(ClientFavoriteProductRepository clientFavoriteProductRepository, ProductService productService) {
        this.clientFavoriteProductRepository = clientFavoriteProductRepository;
        this.productService = productService;
    }

    @Override
    public ClientFavoriteProduct addClientFavoriteProduct(ClientFavoriteProductRecordDTO requestBody) throws ConflictException, NotFoundException {
        this.getProductDetails(requestBody.productId());

        var productAlreadyAdded = this.clientFavoriteProductRepository.findByClientIdAndProductId(requestBody.clientId(), requestBody.productId());
        if (productAlreadyAdded.isPresent()) {
            throw new ConflictException("Produto já cadastrado para o cliente!");
        }

        var clientFavoriteProduct = new ClientFavoriteProduct();
        BeanUtils.copyProperties(requestBody, clientFavoriteProduct);

        return this.clientFavoriteProductRepository.save(clientFavoriteProduct);
    }

    @Override
    public void deleteClientFavoriteProduct(Integer id, String productId) throws NotFoundException {
        var productExistsForClient = this.clientFavoriteProductRepository.findByClientIdAndProductId(id, productId);

        if (productExistsForClient.isEmpty()) {
            throw new NotFoundException("Produto não existe na lista do cliente!");
        }

        this.clientFavoriteProductRepository.deleteByClientIdAndProductId(id, productId);
    }

    @Override
    public PaginatedRecordDTO<ProductRecordDTO> getClientFavoriteProducts(Integer id, Integer page, Integer pageSize) throws NotFoundException {
        var response = this.clientFavoriteProductRepository.findAllByClientId(id, PageRequest.of((page - 1), pageSize));

        var pagination = PaginationRecordDTO.builder().page(response.getPageable().getPageNumber() + 1).pageSize(response.getPageable().getPageSize()).totalElements(response.getTotalElements()).build();

        var products = new ArrayList<ProductRecordDTO>();
        for (ClientFavoriteProduct product : response.toList()) {
            var productDetails = this.getProductDetails(product.getProductId());

            products.add(productDetails);
        }

        return new PaginatedRecordDTO<>(pagination, products);
    }

    private ProductRecordDTO getProductDetails(String id) throws NotFoundException {
        try {
            return this.productService.getProductDetails(id);
        } catch (Exception e) {
            throw new NotFoundException("Produto não encontrado!");
        }
    }
}
