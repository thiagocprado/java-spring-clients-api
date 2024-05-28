package com.luizalabs.api.clients.service;

import com.luizalabs.api.clients.service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://challenge-api.luizalabs.com/api/product", name = "challenge-api")
public interface ProductService {

    @GetMapping("/{id}/")
    ProductDTO getProductDetails(@PathVariable("id") String id);
}
