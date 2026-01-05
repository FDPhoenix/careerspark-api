package com.khanghn.careerspark_api.service.email;

import com.khanghn.careerspark_api.model.User;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendOtpEmail(User user, String otp);
}
