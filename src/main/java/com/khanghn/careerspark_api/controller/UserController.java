package com.khanghn.careerspark_api.controller;

import com.khanghn.careerspark_api.dto.ResponseObject;
import com.khanghn.careerspark_api.dto.request.user.ChangePasswordRequestDTO;
import com.khanghn.careerspark_api.security.CustomUserDetails;
import com.khanghn.careerspark_api.service.user.UserService;
import lombok.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PutMapping("/password")
    public ResponseObject<String> changePassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ChangePasswordRequestDTO req){
        userService.changePassword(customUserDetails.getId(), req);
        return ResponseObject.success("Password changed successfully!");
    }

    @DeleteMapping("/")
    public ResponseObject<String> changeUserStatus(@RequestParam String userId){
        userService.changeUserStatus(UUID.fromString(userId));
        return ResponseObject.success("Change user's status successfully!");
    }
}
