package com.khanghn.careerspark_api.service.company;

import com.khanghn.careerspark_api.dto.request.company.CompanyRequest;
import com.khanghn.careerspark_api.dto.request.company.CompanyUpdateRequest;
import com.khanghn.careerspark_api.dto.response.company.CompanyResponse;
import com.khanghn.careerspark_api.exception.black.BadRequestException;
import com.khanghn.careerspark_api.mapper.CompanyMapper;
import com.khanghn.careerspark_api.mapper.LocationMapper;
import com.khanghn.careerspark_api.model.Company;
import com.khanghn.careerspark_api.model.Location;
import com.khanghn.careerspark_api.model.User;
import com.khanghn.careerspark_api.repository.CompanyRepository;
import com.khanghn.careerspark_api.repository.LocationRepository;
import com.khanghn.careerspark_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImp implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final UserRepository userRepository;

    @Override
    public List<CompanyResponse> getAllCompanies() {
        return companyMapper.companyListToCompanyResponseList(companyRepository.findAll());
    }

    @Override
    public CompanyResponse createCompany(UUID currentUserId, CompanyRequest req) {
        if (companyRepository.existsByNameIgnoreCase(req.name())) {
            throw new BadRequestException("This company's name already in use!");
        }

        if (req.foundedYear() > LocalDate.now().getYear()) {
            throw new BadRequestException("Founded year exceeds current year!");
        }

        Location location = locationRepository
                .findByCountryAndRegionAndCityAndWard(
                        req.location().country(),
                        req.location().region(),
                        req.location().city(),
                        req.location().ward())
                .orElseGet(() -> {
                    Location newLocation = locationMapper.locationRequestToLocation(req.location());
                    return locationRepository.save(newLocation);
                });

        User creator = userRepository
                .findById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        Company newCompany = companyMapper.companyRequestToCompany(req);
        newCompany.setLocation(location);
        newCompany.setCreatedBy(creator);
        companyRepository.save(newCompany);

        return companyMapper.companyToCompanyResponse(newCompany);
    }

    @Override
    public CompanyResponse updateCompany(UUID id, CompanyUpdateRequest req) {
        return null;
    }
}
