package com.luizalabs.api.clients.api.v1.dto.client.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllClientsRequestDTO {
    protected Integer page = 1;
    protected Integer pageSize = 7;
}
