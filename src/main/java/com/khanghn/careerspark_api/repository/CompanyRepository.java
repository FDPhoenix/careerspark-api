package com.khanghn.careerspark_api.repository;

import com.khanghn.careerspark_api.model.Company;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<@NonNull Company, @NonNull UUID> {
    boolean existsByNameIgnoreCase(String name);
}
