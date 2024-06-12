package com.luizalabs.api.clients.service;

import com.luizalabs.api.clients.dto.ProductRecordDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${feign.challengeApi.url}/product", name = "${feign.challengeApi.name}")
public interface ProductService {

    @GetMapping("/{id}/")
    ProductRecordDTO getProductDetails(@PathVariable String id);
}
