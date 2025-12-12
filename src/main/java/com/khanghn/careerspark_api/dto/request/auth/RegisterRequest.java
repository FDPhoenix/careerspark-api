package com.khanghn.careerspark_api.dto.request.auth;

import com.khanghn.careerspark_api.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 6)
        String password,

        String fullName,

        @NotNull
        User.Role role
) {}
