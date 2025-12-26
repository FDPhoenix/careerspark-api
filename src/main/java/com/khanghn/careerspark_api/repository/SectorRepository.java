package com.khanghn.careerspark_api.repository;

import com.khanghn.careerspark_api.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SectorRepository extends JpaRepository<Sector, UUID> {
    List<Sector> findByIsAvailable(boolean isAvailable);
    boolean existsBySlug(String slug);
}
