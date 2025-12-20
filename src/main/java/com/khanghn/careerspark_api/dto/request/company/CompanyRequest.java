package com.khanghn.careerspark_api.dto.request.company;

import com.khanghn.careerspark_api.dto.request.location.LocationRequest;
import com.khanghn.careerspark_api.model.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyRequest(
        @NotBlank(message = "Company name is mandatory!")
        String name,

        String website,
        String description,
        String logoUrl,
        Integer foundedYear,

        @NotNull(message = "Company size is require!")
        Company.Size size,

        @NotNull(message = "Location is mandatory!")
        LocationRequest location
) {
}
