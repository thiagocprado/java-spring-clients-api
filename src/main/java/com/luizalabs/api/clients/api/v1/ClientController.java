package com.luizalabs.api.clients.api.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luizalabs.api.clients.api.v1.dto.client.request.CreateClientRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.UpdateClientRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.response.ClientResponseDTO;
import com.luizalabs.api.clients.common.controller.BaseController;
import com.luizalabs.api.clients.common.dto.DefaultResponseDTO;
import com.luizalabs.api.clients.exception.ConflictException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.usecase.ClientUseCaseInterface;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController extends BaseController {
    private final ClientUseCaseInterface clientUseCase;

    @Autowired
    public ClientController(ClientUseCaseInterface clientUseCase) {
        this.clientUseCase = clientUseCase;
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<DefaultResponseDTO> createClient(@Valid @RequestBody CreateClientRequestDTO requestBody) throws ConflictException, JsonProcessingException {
        var response = this.clientUseCase.createClient(requestBody);

        return this.buildResponse(HttpStatus.CREATED, this.deserializeToModel(response, ClientResponseDTO.class));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO> deleteClient(@PathVariable Integer id) throws NotFoundException {
        this.clientUseCase.deleteClient(id);

        return this.buildResponse(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @GetMapping("/")
    public ResponseEntity<DefaultResponseDTO> getAllClients(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) throws JsonProcessingException {
        var response = this.clientUseCase.getAllClients(page, pageSize);

        return this.buildResponse(HttpStatus.OK, this.serializeToDto(response.getResults(), ClientResponseDTO.class), response.getPagination());
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO> getClientById(@PathVariable int id) throws NotFoundException, JsonProcessingException {
        var response = this.clientUseCase.getClientById(id);

        return this.buildResponse(HttpStatus.OK, this.deserializeToModel(response, ClientResponseDTO.class));
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO> updateClient(@PathVariable Integer id, @Valid @RequestBody UpdateClientRequestDTO requestBody) throws NotFoundException, ConflictException, JsonProcessingException {
        var response = this.clientUseCase.updateClient(id, requestBody);

        return this.buildResponse(HttpStatus.OK, this.deserializeToModel(response, ClientResponseDTO.class));
    }
}
