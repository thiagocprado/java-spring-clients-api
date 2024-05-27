package com.luizalabs.api.clients.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "TB_CLIENTS_FAVORITE_PRODUCTS")
public class ClientFavoriteProduct implements Serializable {
    @Serial
    private static final long serialVersionUID = 2763385211076537126L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "product_id")
    private String productId;
}
