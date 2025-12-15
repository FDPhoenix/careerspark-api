package com.khanghn.careerspark_api.dto.request.subscription;

public record SubscriptionPackageUpdateRequest(
        String name,
        Integer price,
        Integer jobPostLimit,
        Integer featuredJobLimit,
        Integer jobPostDuration,
        Integer durationDays,
        String description
) {
}
