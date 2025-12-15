package com.khanghn.careerspark_api.dto.response.subscription;

import java.time.Instant;
import java.util.UUID;

public record SubscriptionPackageResponse(
        UUID id,
        String name,
        String price,
        Integer jobPostLimit,
        Integer featuredJobLimit,
        Integer jobPostDuration,
        Integer durationDays,
        String description,
        Instant createdAt
) {
}
