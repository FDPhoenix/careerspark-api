package com.khanghn.careerspark_api.exception.handler;

import com.khanghn.careerspark_api.util.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull AuthenticationException authException
    ) throws IOException {
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

        ResponseWriter.write(
                response,
                HttpServletResponse.SC_UNAUTHORIZED,
                defaultCode,
                defaultMessage
        );
    }
}
