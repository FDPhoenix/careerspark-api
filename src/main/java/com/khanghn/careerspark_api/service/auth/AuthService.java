package com.khanghn.careerspark_api.service.auth;

import com.khanghn.careerspark_api.dto.request.auth.LoginRequest;
import com.khanghn.careerspark_api.dto.request.auth.RegisterRequest;
import com.khanghn.careerspark_api.dto.response.auth.AccessToken;
import com.khanghn.careerspark_api.dto.response.auth.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AccessToken reissueAccessToken(String refreshToken);
    void logout(String refreshToken);
}
