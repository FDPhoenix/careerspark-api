package com.khanghn.careerspark_api.security;

import java.util.List;

public final class PublicEndpoints {
    private PublicEndpoints() {}

    public static final String[] PERMIT_ENDPOINTS = {
            "/auth/login",
            "/auth/register",
            "/auth/refresh",
            "/auth/verify-otp",
            "/auth/resend-otp"
    };

    public static final String[] SWAGGER_UI_ENDPOINTS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    public static final List<String> PERMIT_ENDPOINT_LIST = List.of(PERMIT_ENDPOINTS);
}
