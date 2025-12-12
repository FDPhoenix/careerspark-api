package com.khanghn.careerspark_api.service.otp;

import com.khanghn.careerspark_api.exception.black.BadRequestException;
import com.khanghn.careerspark_api.model.User;
import com.khanghn.careerspark_api.model.VerificationOtp;
import com.khanghn.careerspark_api.repository.UserRepository;
import com.khanghn.careerspark_api.repository.VerificationOtpRepository;
import com.khanghn.careerspark_api.service.email.EmailService;
import com.khanghn.careerspark_api.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationOtpServiceImp implements VerificationOtpService {
    private final VerificationOtpRepository verificationOtpRepository;
    private final EmailService emailService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public String generateVerificationOtp(User user) {
        //Generate random string
        SecureRandom random = new SecureRandom();
        String otp = String.valueOf(random.nextInt(900000) + 100000);

        //Save OTP in database
        VerificationOtp verificationOtp = new VerificationOtp();
        verificationOtp.setOtp(otp);
        verificationOtp.setUser(user);
        verificationOtp.setExpiryDate(Instant.now().plusSeconds(600));
        verificationOtpRepository.save(verificationOtp);

        return otp;
    }

    @Override
    @Transactional
    public void resendOtp(String email) {
        //Find user by email
        User user = userService.findByEmail(email);

        //Delete the user's old OTP
        verificationOtpRepository.deleteAllVerificationOtpByUser(user);

        //Generate new OTP and save to database
        String newOtp = generateVerificationOtp(user);

        //Send new OTP email
        emailService.sendOtpEmail(user, newOtp);
    }

    @Override
    public void verifyOtp(String otp) {
        //Verify the correctness of the OTP
        VerificationOtp verificationOtp = verificationOtpRepository
                .findByOtp(otp)
                .orElseThrow(() -> new BadRequestException("Invalid OTP"));

        //Check the expiration of the OTP
        if (verificationOtp.getExpiryDate().isBefore(Instant.now())) {
            throw new BadRequestException("OTP expired");
        }

        //If the OTP is valid, set the otp's user isVerify = true
        User user = verificationOtp.getUser();
        user.setVerified(true);
        userRepository.save(user);

        //Delete OTP after verify
        verificationOtpRepository.delete(verificationOtp);
    }
}
