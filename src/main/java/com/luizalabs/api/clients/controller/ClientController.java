package com.luizalabs.api.clients.controller;

import com.luizalabs.api.clients.dto.ClientRecordDTO;
import com.luizalabs.api.clients.dto.PaginatedRecordDTO;
import com.luizalabs.api.clients.entity.Client;
import com.luizalabs.api.clients.exception.ConflictException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.usecase.ClientUseCaseInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientUseCaseInterface clientUseCase;

    @Autowired
    public ClientController(ClientUseCaseInterface clientUseCase) {
        this.clientUseCase = clientUseCase;
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientRecordDTO requestBody) throws ConflictException {
        var response = this.clientUseCase.createClient(requestBody);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder deleteClient(@PathVariable Integer id) throws NotFoundException {
        this.clientUseCase.deleteClient(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<PaginatedRecordDTO<Client>> getAllClients(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        var response = this.clientUseCase.getAllClients(page, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id) throws NotFoundException {
        var response = this.clientUseCase.getClientById(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer id, @Valid @RequestBody ClientRecordDTO requestBody) throws NotFoundException, ConflictException {
        var response = this.clientUseCase.updateClient(id, requestBody);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
