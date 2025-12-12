package com.khanghn.careerspark_api.dto.response.auth;

import java.time.Instant;

public record AccessToken(
        String token,
        Instant expiresAt
) {}
