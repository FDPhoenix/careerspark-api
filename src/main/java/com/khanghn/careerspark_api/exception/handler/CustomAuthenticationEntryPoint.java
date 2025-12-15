package com.khanghn.careerspark_api.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull AuthenticationException authException) throws IOException {
        String jwtError = (String) request.getAttribute("JWT_ERROR");
        String defaultCode = "UNAUTHORIZED";
        String defaultMessage = "No token provided!";

        if ("EXPIRED".equals(jwtError)) {
            defaultCode = "TOKEN_EXPIRED";
            defaultMessage = "Access token has expired!";
        } else if ("INVALID".equals(jwtError)) {
            defaultCode = "INVALID_TOKEN";
            defaultMessage = "Token is invalid!";
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", defaultCode);
        body.put("message", defaultMessage);
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
