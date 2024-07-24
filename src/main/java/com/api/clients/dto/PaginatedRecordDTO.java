package com.api.clients.dto;

import java.util.List;

public record PaginatedRecordDTO<T>(PaginationRecordDTO meta, List<T> results) {
}
