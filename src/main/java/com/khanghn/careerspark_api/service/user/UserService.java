package com.khanghn.careerspark_api.service.user;

import com.khanghn.careerspark_api.dto.request.user.ChangePasswordRequestDTO;
import com.khanghn.careerspark_api.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {
    User findByEmail(String email);
    void changePassword(UUID userIdFromToken, UUID pathUserId, ChangePasswordRequestDTO req);
    void changeUserStatus(UUID userId);
}
