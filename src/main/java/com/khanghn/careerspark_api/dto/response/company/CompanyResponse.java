package com.khanghn.careerspark_api.dto.response.company;

import com.khanghn.careerspark_api.dto.response.location.LocationResponse;
import com.khanghn.careerspark_api.dto.response.user.UserResponse;
import com.khanghn.careerspark_api.model.Company;

import java.time.Instant;
import java.util.UUID;

public record CompanyResponse(
        UUID id,
        String name,
        String website,
        String description,
        String logoUrl,
        Integer foundedYear,
        Company.Size size,
        LocationResponse location,
        UserResponse createdBy,
        boolean isVerified,
        Instant createdAt,
        Instant updatedAt
) {
}
