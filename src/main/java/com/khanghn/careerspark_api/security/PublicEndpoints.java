package com.khanghn.careerspark_api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

@RequiredArgsConstructor
public final class PublicEndpoints {

    public static final PathPatternRequestMatcher[] PUBLIC_ENDPOINTS = {
            PathPatternRequestMatcher.pathPattern(HttpMethod.GET, "/packages/**"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.POST, "/auth/login"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.POST, "/auth/register"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.POST, "/auth/refresh"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.POST, "/auth/verify-otp"),
            PathPatternRequestMatcher.pathPattern(HttpMethod.POST, "/auth/resend-otp"),
    };

    public static final List<RequestMatcher> PUBLIC_ENDPOINT_LIST = List.of(PUBLIC_ENDPOINTS);
}
