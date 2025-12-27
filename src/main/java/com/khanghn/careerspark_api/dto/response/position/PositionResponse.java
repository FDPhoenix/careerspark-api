package com.khanghn.careerspark_api.dto.response.position;

import com.khanghn.careerspark_api.dto.response.sector.SectorResponse;

import java.util.UUID;

public record PositionResponse(
        UUID id,
        String name,
        String slug,
        UUID sectorId,
        long jobNumber,
        Integer sortOrder,
        boolean isAvailable
) {
}
