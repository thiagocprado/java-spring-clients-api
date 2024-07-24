package com.api.clients.repository;

import com.api.clients.entity.Client;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("clientRepository")
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(@NonNull String email);
}