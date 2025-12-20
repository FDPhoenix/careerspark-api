package com.khanghn.careerspark_api.dto.request.location;

import jakarta.validation.constraints.NotBlank;

public record LocationRequest(
        @NotBlank(message = "Country is required!")
        String country,

        String region,

        @NotBlank(message = "City is required!")
        String city,

        @NotBlank(message = "Ward is required!")
        String ward
) {
}
