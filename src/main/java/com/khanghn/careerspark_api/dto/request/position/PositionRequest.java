package com.khanghn.careerspark_api.dto.request.position;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record PositionRequest(
        @NotBlank(message = "Position name is mandatory!")
        String name,

        @NotBlank(message = "Slug is mandatory!")
        String slug,

        UUID sectorId,
        Integer sortOrder,
        boolean isAvailable
) {
}
