package com.rs.authenticationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.rs.authenticationservice.dto.request.CreateUserRequest;
import com.rs.authenticationservice.dto.request.UpdateUserRequest;
import com.rs.authenticationservice.dto.response.UserResponse;
import com.rs.authenticationservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(CreateUserRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UpdateUserRequest request);
}
