package com.luizalabs.api.clients.seeder;

import com.luizalabs.api.clients.dto.ClientFavoriteProductRecordDTO;
import com.luizalabs.api.clients.dto.ProductRecordDTO;
import com.luizalabs.api.clients.entity.ClientFavoriteProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;

public class ClientFavoriteProductSeeder {
    // dtos
    public static ClientFavoriteProductRecordDTO addClientFavoriteProductRequestDTO() {
        return ClientFavoriteProductRecordDTO.builder()
                .clientId(1)
                .productId("product-id-1")
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
    public static ProductRecordDTO productRecordDTO() {
        return ProductRecordDTO.builder()
                .brand("marca 1")
                .id("product-id-1")
                .image("image-id-1.png")
                .title("product 1")
                .price(BigDecimal.TEN)
                .build();
    }
}
