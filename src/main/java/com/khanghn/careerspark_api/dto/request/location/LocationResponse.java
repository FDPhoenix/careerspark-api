package com.khanghn.careerspark_api.dto.request.location;

import java.util.UUID;

public record LocationResponse(
        UUID id,
        String country,
        String region,
        String city,
        String ward
) {
}
