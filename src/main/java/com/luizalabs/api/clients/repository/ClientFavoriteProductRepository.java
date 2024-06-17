package com.luizalabs.api.clients.repository;

import com.luizalabs.api.clients.entity.ClientFavoriteProduct;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("clientFavoriteProductRepository")
public interface ClientFavoriteProductRepository extends JpaRepository<ClientFavoriteProduct, Integer> {
    Optional<ClientFavoriteProduct> findByClientIdAndProductId(Integer clientId, String productId);

    void deleteByClientIdAndProductId(Integer clientId, String productId);

    void deleteByClientId(Integer clientId);

    Page<ClientFavoriteProduct> findAllByClientId(Integer clientId, Pageable pageable);
}
