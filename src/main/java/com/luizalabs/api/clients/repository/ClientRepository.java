package com.luizalabs.api.clients.repository;

import com.luizalabs.api.clients.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository("clientRepository")
public interface ClientRepository extends JpaRepository<Client, BigInteger> {

}