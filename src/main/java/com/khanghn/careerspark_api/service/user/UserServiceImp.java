package com.khanghn.careerspark_api.service.user;

import com.khanghn.careerspark_api.dto.request.user.ChangePasswordRequestDTO;
import com.khanghn.careerspark_api.exception.black.BadRequestException;
import com.khanghn.careerspark_api.model.User;
import com.khanghn.careerspark_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public void changePassword(UUID userIdFromToken, UUID pathUserId, ChangePasswordRequestDTO req) {
        User user = userRepository
                .findById(pathUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        if (userIdFromToken.equals(pathUserId)) throw new AccessDeniedException("You are not allowed to change other user's password!");

        if (passwordEncoder.matches(req.currentPassword(), user.getPasswordHash())) {
            if (req.newPassword().equals(req.confirmNewPassword())) {
                user.setPasswordHash(passwordEncoder.encode(req.newPassword()));
                userRepository.save(user);
            } else {
                throw new BadRequestException("New password and confirm password don't match!");
            }
        } else throw new BadRequestException("Current Password Mismatch!");
    }

    @Override
    public void changeUserStatus(UUID userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        boolean currentStatus = user.isActive();
        user.setActive(!currentStatus);

        userRepository.save(user);
    }
}
