package com.khanghn.careerspark_api.dto.request.position;

public record PositionUpdateRequest(
        String name,
        String slug,
        String sectorId,
        Integer sortOrder,
        boolean available
) {
}
