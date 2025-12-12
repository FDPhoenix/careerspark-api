package com.khanghn.careerspark_api.dto.request.auth;

public record LoginRequest(
        String email,
        String password
) {}