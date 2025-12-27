package com.khanghn.careerspark_api.dto.response.sector;

import java.util.UUID;

public record SectorResponse(
        UUID id,
        String name,
        String slug,
        String iconUrl,
        String description,
        long jobNumber,
        Integer sortOrder,
        boolean isAvailable
) {
}
