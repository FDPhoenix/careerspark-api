package com.khanghn.careerspark_api.controller;

import com.khanghn.careerspark_api.dto.ResponseObject;
import com.khanghn.careerspark_api.dto.request.company.CompanyRequest;
import com.khanghn.careerspark_api.dto.request.company.CompanyUpdateRequest;
import com.khanghn.careerspark_api.dto.response.company.CompanyResponse;
import com.khanghn.careerspark_api.exception.black.UnauthorizedException;
import com.khanghn.careerspark_api.security.CustomUserDetails;
import com.khanghn.careerspark_api.service.company.CompanyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/")
    public ResponseObject<List<CompanyResponse>> listAllCompany(){
        return ResponseObject.success(companyService.getAllCompanies());
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/")
    public ResponseObject<CompanyResponse> createCompany(@Valid @RequestBody CompanyRequest req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthorized");
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        assert customUserDetails != null;
        UUID currentUserId = customUserDetails.getId();

        return ResponseObject.success(companyService.createCompany(currentUserId, req));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/")
    public ResponseObject<CompanyResponse> updateCompany(@RequestParam String id, @Valid @RequestBody CompanyUpdateRequest req){
        return ResponseObject.success(companyService.updateCompany(UUID.fromString(id), req));
    }
}
