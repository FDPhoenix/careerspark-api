package com.khanghn.careerspark_api.controller;

import com.khanghn.careerspark_api.dto.ResponseObject;
import com.khanghn.careerspark_api.dto.request.auth.LoginRequest;
import com.khanghn.careerspark_api.dto.request.auth.RefreshRequest;
import com.khanghn.careerspark_api.dto.request.auth.RegisterRequest;
import com.khanghn.careerspark_api.dto.response.auth.AccessToken;
import com.khanghn.careerspark_api.dto.response.auth.AuthResponse;
import com.khanghn.careerspark_api.service.auth.AuthService;
import com.khanghn.careerspark_api.service.otp.VerificationOtpService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final VerificationOtpService verificationOtpService;

    @PostMapping("/register")
    public ResponseObject<String> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseObject.success("Register successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseObject<String> verifyOTP(@RequestParam String otp) {
        verificationOtpService.verifyOtp(otp);
        return ResponseObject.success("Email verified successfully");
    }

    @PostMapping("/resend-otp")
    public ResponseObject<String> resendOTP(@RequestParam String email) {
        verificationOtpService.resendOtp(email);
        return ResponseObject.success("Email resend successfully");
    }

    @PostMapping("/login")
    public ResponseObject<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseObject.success(
                "Login successfully",
                authResponse
        );
    }

    @PostMapping("/refresh")
    public ResponseObject<AccessToken> reissueAccessToken(@RequestBody RefreshRequest req) {
        AccessToken accessToken = authService.reissueAccessToken(req.refreshToken());
        return ResponseObject.success(
                "Reissue access token successfully",
                accessToken
        );
    }

    @PostMapping("/logout")
    @SecurityRequirement(name = "bearer")
    public ResponseObject<String> logout(@RequestBody RefreshRequest req) {
        authService.logout(req.refreshToken());
        return ResponseObject.success("Logout successfully");
    }
}
