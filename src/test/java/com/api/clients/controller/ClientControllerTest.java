package com.api.clients.controller;

import com.api.clients.BaseTests;
import com.api.clients.MySqlContainer;
import com.api.clients.dto.ClientRecordDTO;
import com.api.clients.entity.Client;
import com.api.clients.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MySqlContainer.class)
public class ClientControllerTest extends BaseTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ClientRepository clientRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void createClient() throws Exception {
        var client = ClientRecordDTO.builder().name("criat cliente teste").email("criarclienteteste@email.com").build();

        this.mvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(client.name()))
                .andExpect(jsonPath("$.email").value(client.email()));
    }

    @Test
    void deleteClient() throws Exception {
        var client = Client.builder().name("deletar cliente teste").email("deletarclienteteste@email.com").build();
        clientRepository.save(client);

        this.mvc.perform(delete("/api/v1/clients/" + client.getId()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void getAllClients() throws Exception {
        var client = Client.builder().name("buscar cliente um").email("buscarclienteum@email.com").build();
        clientRepository.save(client);

        var clientTwo = Client.builder().name("buscar cliente dois").email("buscarclientedois@email.com").build();
        clientRepository.save(clientTwo);

        this.mvc.perform(get("/api/v1/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getClientById() throws Exception {
        var client = Client.builder().name("buscar cliente").email("buscarcliente@email.com").build();
        clientRepository.save(client);

        this.mvc.perform(get("/api/v1/clients/" + client.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.name").value(client.getName()))
                .andExpect(jsonPath("$.email").value(client.getEmail()));
    }

    @Test
    void updateClient() throws Exception {
        var client = Client.builder().name("atualizar cliente").email("atualizarcliente@email.com").build();
        clientRepository.save(client);

        var update = Client.builder().name("cliente atualizado").email("clienteatualizado@email.com").build();

        this.mvc.perform(put("/api/v1/clients/" + client.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.name").value(update.getName()))
                .andExpect(jsonPath("$.email").value(update.getEmail()));
    }
}
