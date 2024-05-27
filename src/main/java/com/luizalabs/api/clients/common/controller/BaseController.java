package com.luizalabs.api.clients.common.controller;

import com.luizalabs.api.clients.common.dto.DefaultResponseDTO;
import com.luizalabs.api.clients.common.dto.PaginationDTO;
import com.luizalabs.api.clients.common.mapper.BaseMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

public class BaseController extends BaseMapper {
    @Value("${application.name}")
    private String applicationName;
    @Value("${application.version}")
    private String applicationVersion;
    @Value("${application.env}")
    private String applicationEnv;
    @Value("${application.description}")
    private String applicationDescription;

    protected ResponseEntity<DefaultResponseDTO> buildResponse(HttpStatus httpStatus) {
        var response = DefaultResponseDTO.builder()
                .appInfo(this.getDetailApplicationResponse())
                .results(Collections.emptyList())
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }

    protected <T> ResponseEntity<DefaultResponseDTO> buildResponse(HttpStatus httpStatus, T value) {
        var response = DefaultResponseDTO.builder()
                .appInfo(this.getDetailApplicationResponse())
                .results(value)
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }

    protected <T> ResponseEntity<DefaultResponseDTO> buildResponse(HttpStatus httpStatus, T value, PaginationDTO pagination) {
        var response = DefaultResponseDTO.builder()
                .appInfo(this.getDetailApplicationResponse())
                .results(value)
                .pagination(pagination)
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }

    private DefaultResponseDTO.DetailApplicationResponse getDetailApplicationResponse() {
        return DefaultResponseDTO.DetailApplicationResponse.builder()
                .name(this.applicationName)
                .version(this.applicationVersion)
                .env(this.applicationEnv)
                .description(this.applicationDescription)
                .build();
    }
}
