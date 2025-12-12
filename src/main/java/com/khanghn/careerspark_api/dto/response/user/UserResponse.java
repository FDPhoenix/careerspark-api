package com.khanghn.careerspark_api.dto.response.user;

import com.khanghn.careerspark_api.model.User;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String fullName,
        User.Role role,
        boolean verified
) {}
