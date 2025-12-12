package com.khanghn.careerspark_api.service.user;

import com.khanghn.careerspark_api.model.User;
import com.khanghn.careerspark_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public boolean isUserExists(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }
}
