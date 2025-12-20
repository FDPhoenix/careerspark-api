package com.khanghn.careerspark_api.service.company;

import com.khanghn.careerspark_api.dto.request.company.CompanyRequest;
import com.khanghn.careerspark_api.dto.request.company.CompanyUpdateRequest;
import com.khanghn.careerspark_api.dto.response.company.CompanyResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CompanyService {
    List<CompanyResponse> getAllCompanies();
    CompanyResponse createCompany(UUID currentUserId, CompanyRequest companyRequest);
    CompanyResponse updateCompany(UUID id, CompanyUpdateRequest req);
}
