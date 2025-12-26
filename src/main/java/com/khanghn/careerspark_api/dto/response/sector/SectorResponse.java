package com.khanghn.careerspark_api.dto.response.sector;

public record SectorResponse(
        String name,
        String slug,
        String iconUrl,
        String description,
        long jobNumber,
        Integer sortOrder,
        boolean isAvailable
) {
}
