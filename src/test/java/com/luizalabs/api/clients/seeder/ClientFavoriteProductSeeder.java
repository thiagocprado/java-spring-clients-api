package com.luizalabs.api.clients.seeder;

import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.AddClientFavoriteProductRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.DeleteClientFavoriteProductRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.clientFavoriteProduct.request.GetAllClientFavoriteProductsRequestDTO;
import com.luizalabs.api.clients.entity.ClientFavoriteProduct;
import com.luizalabs.api.clients.service.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;

public class ClientFavoriteProductSeeder {
    // dtos
    public static AddClientFavoriteProductRequestDTO addClientFavoriteProductRequestDTO() {
        return AddClientFavoriteProductRequestDTO.builder()
                .clientId(1)
                .productId("product-id-1")
                .build();
    }

    public static DeleteClientFavoriteProductRequestDTO deleteClientFavoriteProductRequestDTO() {
        return DeleteClientFavoriteProductRequestDTO.builder()
                .clientId(1)
                .productId("product-id-1")
                .build();
    }

    public static GetAllClientFavoriteProductsRequestDTO getAllClientFavoriteProductsRequestDTO() {
        return GetAllClientFavoriteProductsRequestDTO.builder()
                .clientId(1)
                .page(1)
                .pageSize(10)
                .build();
    }

    // repositories
    public static Page<ClientFavoriteProduct> clientFavoriteProductsRepository() {
        var clients = Collections.singletonList(
                ClientFavoriteProduct.builder()
                        .id(1)
                        .clientId(1)
                        .productId("product-id-1")
                        .build()
        );

        var pageable = PageRequest.of(0, 10);

        return new PageImpl<>(clients, pageable, clients.size());
    }

    public static ClientFavoriteProduct clientFavoriteProductRepostiory() {
        return ClientFavoriteProduct.builder()
                .id(1)
                .clientId(1)
                .productId("product-id-1")
                .build();
    }

    // services
    public static ProductDTO productDTO() {
        return ProductDTO.builder()
                .brand("marca 1")
                .id("product-id-1")
                .image("image-id-1.png")
                .title("product 1")
                .price(BigDecimal.TEN)
                .build();
    }
}
