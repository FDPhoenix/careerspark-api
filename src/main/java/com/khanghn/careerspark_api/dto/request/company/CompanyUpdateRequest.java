package com.khanghn.careerspark_api.dto.request.company;

import com.khanghn.careerspark_api.dto.request.location.LocationRequest;
import com.khanghn.careerspark_api.model.Company;

public record CompanyUpdateRequest(
        String name,
        String website,
        String description,
        String logo_url,
        Integer founded_year,
        Company.Size size,
        LocationRequest location
) {
}
