package com.khanghn.careerspark_api.repository;

import com.khanghn.careerspark_api.model.CandidateProfile;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CandidateProfileRepository extends JpaRepository<@NonNull CandidateProfile, @NonNull UUID> {
}
