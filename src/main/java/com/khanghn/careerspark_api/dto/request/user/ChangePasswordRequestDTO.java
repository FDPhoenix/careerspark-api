package com.khanghn.careerspark_api.dto.request.user;

public record ChangePasswordRequestDTO(
        String currentPassword,
        String newPassword,
        String confirmNewPassword
) {
}
