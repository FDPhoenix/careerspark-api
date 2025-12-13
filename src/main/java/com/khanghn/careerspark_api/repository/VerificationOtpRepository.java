package com.khanghn.careerspark_api.repository;

import com.khanghn.careerspark_api.model.User;
import com.khanghn.careerspark_api.model.VerificationOtp;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationOtpRepository extends JpaRepository<@NonNull VerificationOtp, @NonNull UUID> {
    Optional<VerificationOtp> findByOtp(String otp);
    void deleteAllVerificationOtpByUser(User user);
}
