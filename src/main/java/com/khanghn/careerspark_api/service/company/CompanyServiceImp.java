package com.khanghn.careerspark_api.service.company;

import com.khanghn.careerspark_api.dto.request.company.CompanyRequest;
import com.khanghn.careerspark_api.dto.request.company.CompanyUpdateRequest;
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
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company createCompany(UUID currentUserId, CompanyRequest req) {
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

        return newCompany;
    }

    @Override
    public Company updateCompany(UUID id, CompanyUpdateRequest req) {
        Company existsCompany = companyRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found!"));

        if (req.name() != null && !req.name().trim().isEmpty()) {
            String newName = req.name().trim();
            if (!existsCompany.getName().equalsIgnoreCase(newName)) {
                if (companyRepository.existsByNameIgnoreCase(newName)) {
                    throw new BadRequestException("Company's name already exists!");
                }
            }
        }

        if (req.founded_year() != null) {
            if (req.founded_year() > LocalDate.now().getYear())
                throw new BadRequestException("Founded year exceeds current year!");
        }

        companyMapper.updateFromRequest(req, existsCompany);

        Location location = locationRepository
                .findByCountryAndRegionAndCityAndWard(
                        existsCompany.getLocation().getCountry(),
                        existsCompany.getLocation().getRegion(),
                        existsCompany.getLocation().getCity(),
                        existsCompany.getLocation().getWard()
                ).orElseGet(() -> {
                        Location newLocation = Location.builder()
                                .country(existsCompany.getLocation().getCountry())
                                .region(existsCompany.getLocation().getRegion())
                                .city(existsCompany.getLocation().getCity())
                                .ward(existsCompany.getLocation().getWard())
                                .build();
                        return locationRepository.save(newLocation);
                });
        existsCompany.setLocation(location);

        return companyRepository.save(existsCompany);
    }
}
