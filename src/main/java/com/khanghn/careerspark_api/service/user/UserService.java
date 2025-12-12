package com.khanghn.careerspark_api.service.user;

import com.khanghn.careerspark_api.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByEmail(String email);
    boolean isUserExists(String email);
}
