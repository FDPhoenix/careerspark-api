package com.khanghn.careerspark_api.controller;

import com.khanghn.careerspark_api.dto.ResponseObject;
import com.khanghn.careerspark_api.dto.request.user.ChangePasswordRequestDTO;
import com.khanghn.careerspark_api.security.CustomUserDetails;
import com.khanghn.careerspark_api.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.base-path}")
public class UserController {
    private final UserService userService;

    @PutMapping("/users/{userId}/password")
    @SecurityRequirement(name = "bearer")
    public ResponseObject<String> changePassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String userId, @RequestBody ChangePasswordRequestDTO req){
        userService.changePassword(customUserDetails.getId(), UUID.fromString(userId), req);
        return ResponseObject.success("Password changed successfully!");
    }

    @PatchMapping("/users/{userId}/active")
    @SecurityRequirement(name = "bearer")
    public ResponseObject<String> changeUserStatus(@PathVariable String userId){
        userService.changeUserStatus(UUID.fromString(userId));
        return ResponseObject.success("Change user's status successfully!");
    }
}
