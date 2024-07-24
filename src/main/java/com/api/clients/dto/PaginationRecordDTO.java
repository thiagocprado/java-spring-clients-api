package com.api.clients.dto;

import lombok.Builder;

@Builder
public record PaginationRecordDTO(Integer page, Integer pageSize, Long totalElements) {
}
