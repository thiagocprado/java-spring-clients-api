package com.luizalabs.api.clients.api.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luizalabs.api.clients.api.v1.dto.client.request.CreateClientRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.DeleteClientRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.GetAllClientsRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.GetClientDetailsRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.request.UpdateClientRequestDTO;
import com.luizalabs.api.clients.api.v1.dto.client.response.ClientResponseDTO;
import com.luizalabs.api.clients.common.controller.BaseController;
import com.luizalabs.api.clients.common.dto.DefaultResponseDTO;
import com.luizalabs.api.clients.exception.BadRequestException;
import com.luizalabs.api.clients.exception.ConflictException;
import com.luizalabs.api.clients.exception.NotFoundException;
import com.luizalabs.api.clients.usecase.ClientUseCaseInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController extends BaseController {
    private final ClientUseCaseInterface clientUseCase;

    @Autowired
    public ClientController(ClientUseCaseInterface clientUseCase) {
        this.clientUseCase = clientUseCase;
    }

    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<DefaultResponseDTO> createClient(@Valid @RequestBody CreateClientRequestDTO requestBody) throws ConflictException, JsonProcessingException {
        var response = this.clientUseCase.createClient(requestBody);

        return this.buildResponse(HttpStatus.CREATED, this.deserializeToModel(response, ClientResponseDTO.class));
    }

    @ResponseBody
    @DeleteMapping("/delete")
    public ResponseEntity<DefaultResponseDTO> deleteClient(@Valid DeleteClientRequestDTO requestParams) throws NotFoundException {
        this.clientUseCase.deleteClient(requestParams.getId());

        return this.buildResponse(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @GetMapping("/list")
    public ResponseEntity<DefaultResponseDTO> getAllClients(@Valid GetAllClientsRequestDTO requestParams) throws JsonProcessingException {
        var response = this.clientUseCase.getAllClients(requestParams);

        return this.buildResponse(HttpStatus.OK, this.serializeToDto(response.getResults(), ClientResponseDTO.class), response.getPagination());
    }

    @ResponseBody
    @GetMapping("/details")
    public ResponseEntity<DefaultResponseDTO> getClientById(@Valid GetClientDetailsRequestDTO requestParams) throws NotFoundException, JsonProcessingException {
        var response = this.clientUseCase.getClientById(requestParams.getId());

        return this.buildResponse(HttpStatus.OK, this.deserializeToModel(response, ClientResponseDTO.class));
    }

    @ResponseBody
    @PutMapping("/update")
    public ResponseEntity<DefaultResponseDTO> updateClient(@Valid @RequestBody UpdateClientRequestDTO requestBody) throws NotFoundException, BadRequestException, JsonProcessingException {
        var response = this.clientUseCase.updateClient(requestBody);

        return this.buildResponse(HttpStatus.OK, this.deserializeToModel(response, ClientResponseDTO.class));
    }
}
