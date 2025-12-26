package com.khanghn.careerspark_api.controller;

import com.khanghn.careerspark_api.dto.ResponseObject;
import com.khanghn.careerspark_api.dto.request.company.CompanyRequest;
import com.khanghn.careerspark_api.dto.request.company.CompanyUpdateRequest;
import com.khanghn.careerspark_api.dto.response.company.CompanyResponse;
import com.khanghn.careerspark_api.exception.black.UnauthorizedException;
import com.khanghn.careerspark_api.mapper.CompanyMapper;
import com.khanghn.careerspark_api.model.Company;
import com.khanghn.careerspark_api.security.CustomUserDetails;
import com.khanghn.careerspark_api.service.company.CompanyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @GetMapping("/")
    public ResponseObject<List<CompanyResponse>> listAllCompany() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseObject.success(
                "Fetch all companies successfully",
                companyMapper.companyListToCompanyResponseList(companies)
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/")
    public ResponseObject<CompanyResponse> createCompany(@Valid @RequestBody CompanyRequest req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthorized");
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Objects.requireNonNull(customUserDetails);
        UUID currentUserId = customUserDetails.getId();

        Company newCompany = companyService.createCompany(currentUserId, req);
        return ResponseObject.success(
                "Create company successfully",
                companyMapper.companyToCompanyResponse(newCompany)
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/")
    public ResponseObject<CompanyResponse> updateCompany(@RequestParam String id, @Valid @RequestBody CompanyUpdateRequest req) {
        Company updateCompany = companyService.updateCompany(UUID.fromString(id), req);
        return ResponseObject.success(
                "Update company successfully",
                companyMapper.companyToCompanyResponse(updateCompany)
        );
    }
}
