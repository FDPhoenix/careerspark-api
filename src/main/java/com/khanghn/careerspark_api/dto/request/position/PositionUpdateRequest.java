package com.khanghn.careerspark_api.dto.request.position;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record PositionUpdateRequest(
        String name,
        String slug,
        UUID sectorId,
        Integer sortOrder,
        boolean isAvailable
) {
}
