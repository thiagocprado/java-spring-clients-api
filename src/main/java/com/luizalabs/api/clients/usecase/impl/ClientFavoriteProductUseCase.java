package com.luizalabs.api.clients.usecase.impl;

import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.AddClientFavoriteProductRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.DeleteClientFavoriteProductRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.GetAllClientFavoriteProductsRequestDTO;
import com.luizalabs.api.clients.common.dto.PaginatedResultDTO;
import com.luizalabs.api.clients.common.dto.PaginationDTO;
import com.luizalabs.api.clients.common.helper.JsonHelper;
import com.luizalabs.api.clients.entity.ClientFavoriteProduct;
import com.luizalabs.api.clients.service.dto.ProductDTO;
import com.luizalabs.api.clients.exception.BadRequestException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.repository.ClientFavoriteProductRepository;
import com.luizalabs.api.clients.service.ProductService;
import com.luizalabs.api.clients.usecase.ClientFavoriteProductUseCaseInterface;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Transactional
public class ClientFavoriteProductUseCase implements ClientFavoriteProductUseCaseInterface {
    private final ClientFavoriteProductRepository clientFavoriteProductRepository;
    private final ProductService productService;
    private final JsonHelper jsonHelper;

    public ClientFavoriteProductUseCase(ClientFavoriteProductRepository clientFavoriteProductRepository, ProductService productService, JsonHelper jsonHelper) {
        this.clientFavoriteProductRepository = clientFavoriteProductRepository;
        this.productService = productService;
        this.jsonHelper = jsonHelper;
    }

    @Override
    public ClientFavoriteProduct addClientFavoriteProduct(@NonNull AddClientFavoriteProductRequestDTO requestBody) throws BadRequestException, NotFoundException {
        this.getProductDetails(requestBody.getProductId());

        var productAlreadyAdded = this.clientFavoriteProductRepository.findByClientIdAndProductId(requestBody.getClientId(), requestBody.getProductId());
        if (productAlreadyAdded.isPresent()) {
            throw new BadRequestException("Produto já cadastrado para o cliente!");
        }

        var clientFavoriteProductJson = this.jsonHelper.convertObjectToJson(requestBody);
        var clientFavoriteProduct = this.jsonHelper.convertJsonToClass(clientFavoriteProductJson, ClientFavoriteProduct.class);

        return this.clientFavoriteProductRepository.save(clientFavoriteProduct);
    }

    @Override
    public void deleteClientFavoriteProduct(@NonNull DeleteClientFavoriteProductRequestDTO requestParams) throws NotFoundException {
        var productExistsForClient = this.clientFavoriteProductRepository.findByClientIdAndProductId(requestParams.getClientId(), requestParams.getProductId());

        if (productExistsForClient.isEmpty()) {
            throw new NotFoundException("Produto não existe na lista do cliente!");
        }

        this.clientFavoriteProductRepository.deleteByClientIdAndProductId(requestParams.getClientId(), requestParams.getProductId());
    }

    @Override
    public PaginatedResultDTO<ProductDTO> getClientFavoriteProducts(@NonNull GetAllClientFavoriteProductsRequestDTO requestParams) throws NotFoundException {
        var response = this.clientFavoriteProductRepository.findAllByClientId(requestParams.getClientId(), PageRequest.of((requestParams.getPage() - 1), requestParams.getPageSize()));

        var pagination = new PaginationDTO(
                response.getPageable().getPageNumber() + 1,
                response.getPageable().getPageSize(),
                response.isEmpty() ? 0 : (int) response.getTotalElements()
        );

        var products = new ArrayList<ProductDTO>();
        for (ClientFavoriteProduct product : response.toList()) {
            var productDetails = this.getProductDetails(product.getProductId());

            products.add(productDetails);
        }

        return new PaginatedResultDTO<>(products, pagination);
    }

    private ProductDTO getProductDetails(@NonNull String id) throws NotFoundException {
        try {
            return this.productService.getProduct(id);
        } catch (Exception e) {
            throw new NotFoundException("Produto não encontrado!");
        }
    }
}
