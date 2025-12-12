package com.khanghn.careerspark_api.service.otp;

import com.khanghn.careerspark_api.model.User;
import org.springframework.stereotype.Service;

@Service
public interface VerificationOtpService {
    String generateVerificationOtp(User user);
    void resendOtp(String email);
    void verifyOtp(String otp);
}
