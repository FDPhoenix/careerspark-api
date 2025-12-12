package com.khanghn.careerspark_api.repository;

import com.khanghn.careerspark_api.model.RefreshToken;
import com.khanghn.careerspark_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByUser(User user);
    Optional<RefreshToken> findByToken(String token);
}
