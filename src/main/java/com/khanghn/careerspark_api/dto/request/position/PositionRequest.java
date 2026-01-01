package com.khanghn.careerspark_api.dto.request.position;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PositionRequest(
        @NotBlank(message = "Position name is mandatory!")
        String name,

        @NotBlank(message = "Slug is mandatory!")
        String slug,

        @NotNull
        String sectorId,

        Integer sortOrder,
        boolean available
) {
}
