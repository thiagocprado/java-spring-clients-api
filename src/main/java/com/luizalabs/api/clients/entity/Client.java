package com.luizalabs.api.clients.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "TB_CLIENTS", schema = "db_clients")
public class Client implements Serializable {
    @Serial
    private static final long serialVersionUID = 5919526437212405855L;

    @Id
    private BigInteger id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;
}
