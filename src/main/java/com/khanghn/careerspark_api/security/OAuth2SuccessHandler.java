package com.khanghn.careerspark_api.security;

import com.khanghn.careerspark_api.dto.response.auth.AuthResponse;
import com.khanghn.careerspark_api.model.User;
import com.khanghn.careerspark_api.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Objects.requireNonNull(userDetails);
        User user = userDetails.getUser();

        AuthResponse authResponse = authService.handleOAuth2Login(user);

        String redirectUrl = "http://localhost:5173/login?success=Welcome back, " + authResponse.user().fullName()
                        + "&accessToken=" + authResponse.accessToken().token()
                        + "&refreshToken=" + authResponse.refreshToken();

        response.sendRedirect(redirectUrl);
    }
}
