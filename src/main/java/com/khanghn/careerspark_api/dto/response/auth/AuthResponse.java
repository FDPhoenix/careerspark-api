package com.khanghn.careerspark_api.dto.response.auth;

import com.khanghn.careerspark_api.dto.response.user.UserResponse;

public record AuthResponse(
        AccessToken accessToken,
        String refreshToken,
        UserResponse user
) {}