package com.khanghn.careerspark_api.mapper;

import com.khanghn.careerspark_api.dto.request.company.CompanyRequest;
import com.khanghn.careerspark_api.dto.request.company.CompanyUpdateRequest;
import com.khanghn.careerspark_api.dto.response.company.CompanyResponse;
import com.khanghn.careerspark_api.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyMapper {
    Company companyRequestToCompany(CompanyRequest companyRequest);
    CompanyResponse companyToCompanyResponse(Company company);
    List<CompanyResponse> companyListToCompanyResponseList(List<Company> companies);
    void updateFromRequest(CompanyUpdateRequest request, @MappingTarget Company entity);
}
