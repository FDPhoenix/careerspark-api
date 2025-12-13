package com.khanghn.careerspark_api.service.auth;

import com.khanghn.careerspark_api.dto.response.auth.AccessToken;
import com.khanghn.careerspark_api.security.CustomUserDetails;
import com.khanghn.careerspark_api.model.RefreshToken;
import com.khanghn.careerspark_api.model.User;
import com.khanghn.careerspark_api.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration-ms}")
    private long accessTokenExpirationMs;

    @Value("${jwt.refresh-expiration-ms}")
    private long refreshTokenExpirationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public AccessToken generateAccessToken(User user) {
        Date expiresAt = new Date(System.currentTimeMillis() + accessTokenExpirationMs);

        String token = Jwts.builder()
                .subject(user.getId().toString())
                .claim("role", user.getRole().name())
                .claim("email", user.getEmail())
                .issuedAt(new Date())
                .expiration(expiresAt)
                .signWith(getSigningKey())
                .compact();

        return new AccessToken(
                token,
                expiresAt.toInstant()
        );
    }

    public String generateRefreshToken(User user) {
        refreshTokenRepository.findByUser(user).ifPresent(refreshTokenRepository::delete);

        String refreshToken = UUID.randomUUID().toString();

        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setToken(refreshToken);
        rt.setExpiryDate(Instant.now().plusMillis(refreshTokenExpirationMs));
        refreshTokenRepository.save(rt);

        return refreshToken;
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    public <T> T extractClaim(String token, String claimName, Class<T> type) {
        return extractAllClaims(token).get(claimName, type);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String userIdFromToken = extractUserId(token);

            if (!(userDetails instanceof CustomUserDetails customUserDetails)) {
                return false;
            }

            UUID userIdFromDetails = customUserDetails.getId();
            boolean notExpired = !isTokenExpired(token);

            return userIdFromToken.equals(userIdFromDetails.toString()) && notExpired;
        } catch (ExpiredJwtException e) {
            log.error("Token expired: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Invalid JWT token: {}", e.toString());
            return false;
        }
    }
}
