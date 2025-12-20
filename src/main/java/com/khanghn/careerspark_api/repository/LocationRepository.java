package com.khanghn.careerspark_api.repository;

import com.khanghn.careerspark_api.model.Location;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<@NonNull Location, @NonNull UUID> {
    Optional<Location> findByCountryAndRegionAndCityAndWard(String country, String region, String city, String ward);
}
