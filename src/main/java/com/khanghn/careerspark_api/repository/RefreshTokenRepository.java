package com.khanghn.careerspark_api.repository;

import com.khanghn.careerspark_api.model.RefreshToken;
import com.khanghn.careerspark_api.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<@NonNull RefreshToken, @NonNull UUID> {
    Optional<RefreshToken> findByUser(User user);
    Optional<RefreshToken> findByToken(String token);
}
