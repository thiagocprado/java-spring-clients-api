package com.api.clients.controller;

import com.api.clients.BaseTests;
import com.api.clients.MySqlContainer;
import com.api.clients.dto.ClientFavoriteProductRecordDTO;
import com.api.clients.entity.Client;
import com.api.clients.entity.ClientFavoriteProduct;
import com.api.clients.repository.ClientFavoriteProductRepository;
import com.api.clients.repository.ClientRepository;
import com.api.clients.seeder.ClientFavoriteProductSeeder;
import com.api.clients.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MySqlContainer.class)
public class ClientFavoriteProductControllerTest extends BaseTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ClientFavoriteProductRepository clientFavoriteProductRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void addClientFavoriteProduct() throws Exception {
        var client = Client.builder().name("adicionar produto cliente").email("adicionarprodutocliente@email.com").build();
        clientRepository.save(client);

        var clientProduct = ClientFavoriteProductRecordDTO.builder().clientId(client.getId()).productId("product-1-id").build();

        var product = ClientFavoriteProductSeeder.productRecordDTO();
        Mockito.when(productService.getProductDetails(anyString())).thenReturn(product);

        this.mvc.perform(post("/api/v1/clients/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientId").value(client.getId()))
                .andExpect(jsonPath("$.productId").value("product-1-id"));
    }

    @Test
    void deleteClientFavoriteProduct() throws Exception {
        var client = Client.builder().name("deletar produto cliente").email("deletarprodutocliente@email.com").build();
        clientRepository.save(client);

        var product = ClientFavoriteProduct.builder().clientId(client.getId()).productId("product-2-id").build();
        clientFavoriteProductRepository.save(product);

        this.mvc.perform(delete("/api/v1/clients/products/" + client.getId() + "/" + product.getProductId())).andExpect(status().isNoContent());
    }

    @Test
    void getClientFavoriteProducts() throws Exception {
        var client = Client.builder().name("buscar produto cliente").email("buscarprodutocliente@email.com").build();
        clientRepository.save(client);

        this.mvc.perform(get("/api/v1/clients/products/" + client.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.length()").value(0))
                .andExpect(jsonPath("$.meta.page").value(1))
                .andExpect(jsonPath("$.meta.pageSize").value(10));
    }
}
