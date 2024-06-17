package com.luizalabs.api.clients;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MySqlContainer implements BeforeAllCallback {
    @Container
    private static MySQLContainer mysql = new MySQLContainer("mysql:latest");

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        mysql.start();

        System.setProperty("server.port", "8080");

        System.setProperty("spring.datasource.url", mysql.getJdbcUrl());
        System.setProperty("spring.datasource.username", mysql.getUsername());
        System.setProperty("spring.datasource.password", mysql.getPassword());
        System.setProperty("spring.datasource.name", mysql.getDatabaseName());

        System.setProperty("spring.flyway.url", mysql.getJdbcUrl());
        System.setProperty("spring.flyway.user", mysql.getUsername());
        System.setProperty("spring.flyway.password", mysql.getPassword());

        System.setProperty("feign.challengeApi.name", "feign");
        System.setProperty("feign.challengeApi.url", "localhost:8081");
    }
}
