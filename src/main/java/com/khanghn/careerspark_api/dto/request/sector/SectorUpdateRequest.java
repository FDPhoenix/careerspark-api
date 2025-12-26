package com.khanghn.careerspark_api.dto.request.sector;

public record SectorUpdateRequest(
        String name,
        String slug,
        String iconUrl,
        String description,
        Integer sortOrder,
        boolean isAvailable
) {}
