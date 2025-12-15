package com.khanghn.careerspark_api.dto.request.subscription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SubscriptionPackageRequest(
        @NotBlank(message = "Package's name is mandatory!")
        String name,

        @NotNull(message = "Price is mandatory!")
        @Positive(message = "Price must be greater than 0")
        Integer price,

        @NotNull(message = "The number of job postings is mandatory!")
        @Positive(message = "At least one job must be allowed!")
        Integer jobPostLimit,

        @NotNull(message = "The number of featured jobs is mandatory!")
        @Positive(message = "The number of featured jobs must be greater than 0!")
        Integer featuredJobLimit,

        @NotNull(message = "Job display time is mandatory!")
        @Positive(message = "The job display time must be at least 1 day!")
        Integer jobPostDuration,

        Integer durationDays,
        String description
) {
}
