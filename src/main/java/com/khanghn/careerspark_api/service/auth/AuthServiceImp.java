package com.khanghn.careerspark_api.service.auth;

import com.khanghn.careerspark_api.dto.request.auth.LoginRequest;
import com.khanghn.careerspark_api.dto.request.auth.RegisterRequest;
import com.khanghn.careerspark_api.dto.response.auth.AccessToken;
import com.khanghn.careerspark_api.dto.response.auth.AuthResponse;
import com.khanghn.careerspark_api.dto.response.user.UserResponse;
import com.khanghn.careerspark_api.exception.black.BadRequestException;
import com.khanghn.careerspark_api.exception.black.UnauthorizedException;
import com.khanghn.careerspark_api.mapper.UserMapper;
import com.khanghn.careerspark_api.model.RefreshToken;
import com.khanghn.careerspark_api.model.User;
import com.khanghn.careerspark_api.repository.RefreshTokenRepository;
import com.khanghn.careerspark_api.repository.UserRepository;
import com.khanghn.careerspark_api.service.email.EmailService;
import com.khanghn.careerspark_api.service.otp.VerificationOtpService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImp  implements AuthService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationOtpService  verificationOtpService;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void register(RegisterRequest req) {
        if (userRepository.existsByEmailIgnoreCase(req.email())) {
            throw new BadRequestException("Email Already Exists");
        }

        User newUser = userMapper.registerRequestToUser(req);
        newUser.setPasswordHash(passwordEncoder.encode(req.password()));

        System.out.println(req);
        System.out.println("New User:" + newUser);

        userRepository.save(newUser);

        String otp = verificationOtpService.generateVerificationOtp(newUser);
        emailService.sendOtpEmail(newUser, otp);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadRequestException("Wrong Password");
        }

        if (!user.isVerified()) {
            throw new BadRequestException("Your account has not been verified. Please check your email.");
        }

        if (!user.isActive()) {
            throw new BadRequestException("Your account has been disabled. Please contact administrator.");
        }

        String refreshToken = jwtService.generateRefreshToken(user);
        AccessToken accessToken = jwtService.generateAccessToken(user);
        UserResponse userResponse = userMapper.userToUserResponse(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                userResponse
        );
    }

    @Override
    public AccessToken reissueAccessToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() -> new EntityNotFoundException("Refresh token not found!"));

        if (token.isRevoked()) throw new UnauthorizedException("Refresh token is revoked!");

        if (token.getExpiryDate().isBefore(Instant.now())) throw new UnauthorizedException("Refresh token is expired!");

        User user = token.getUser();

        return jwtService.generateAccessToken(user);
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).ifPresent(token -> {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        });
    }

    @Override
    public AuthResponse handleOAuth2Login(User user) {
        AccessToken accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        UserResponse userResponse = userMapper.userToUserResponse(user);

        return new AuthResponse(accessToken, refreshToken, userResponse);
    }
}
