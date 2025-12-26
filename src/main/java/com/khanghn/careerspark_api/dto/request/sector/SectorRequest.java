package com.khanghn.careerspark_api.dto.request.sector;

import jakarta.validation.constraints.NotBlank;

public record SectorRequest(
        @NotBlank(message = "Sector name is mandatory!")
        String name,

        @NotBlank(message = "Slug is mandatory!")
        String slug,

        String iconUrl,
        String description,
        Integer sortOrder,
        boolean isAvailable
) {}
