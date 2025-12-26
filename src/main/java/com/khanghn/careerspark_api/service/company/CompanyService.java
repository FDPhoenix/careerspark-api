package com.khanghn.careerspark_api.service.company;

import com.khanghn.careerspark_api.dto.request.company.CompanyRequest;
import com.khanghn.careerspark_api.dto.request.company.CompanyUpdateRequest;
import com.khanghn.careerspark_api.model.Company;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CompanyService {
    List<Company> getAllCompanies();
    Company createCompany(UUID currentUserId, CompanyRequest companyRequest);
    Company updateCompany(UUID id, CompanyUpdateRequest req);
}
