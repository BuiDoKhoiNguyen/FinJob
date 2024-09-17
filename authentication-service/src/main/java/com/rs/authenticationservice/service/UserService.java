package com.rs.authenticationservice.service;

import java.util.List;

import com.rs.authenticationservice.dto.request.CreateUserRequest;
import com.rs.authenticationservice.dto.request.UpdateUserRequest;
import com.rs.authenticationservice.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(CreateUserRequest resquest);

    UserResponse getMyInfo();

    UserResponse updateUser(String userId, UpdateUserRequest request);

    void deleteUser(String userId);

    List<UserResponse> getUsers();

    UserResponse getUser(String userId);
}
