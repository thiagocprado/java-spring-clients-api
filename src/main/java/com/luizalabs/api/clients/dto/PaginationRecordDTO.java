package com.luizalabs.api.clients.dto;

import lombok.Builder;

@Builder
public record PaginationRecordDTO(Integer page, Integer pageSize, Long totalElements) {
}
