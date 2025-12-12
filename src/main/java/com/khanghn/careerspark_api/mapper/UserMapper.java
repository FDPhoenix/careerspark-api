package com.khanghn.careerspark_api.mapper;

import com.khanghn.careerspark_api.dto.response.user.UserResponse;
import com.khanghn.careerspark_api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserResponse userToUserResponse(User user);
    User userResponseToUser(UserResponse userResponse);
}
