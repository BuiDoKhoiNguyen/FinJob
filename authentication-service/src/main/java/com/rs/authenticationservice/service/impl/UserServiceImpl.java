package com.rs.authenticationservice.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rs.authenticationservice.dto.request.CreateUserRequest;
import com.rs.authenticationservice.dto.request.UpdateUserRequest;
import com.rs.authenticationservice.dto.response.UserResponse;
import com.rs.authenticationservice.entity.Role;
import com.rs.authenticationservice.entity.User;
import com.rs.authenticationservice.enums.RoleEnum;
import com.rs.authenticationservice.exception.AppException;
import com.rs.authenticationservice.exception.ErrorCode;
import com.rs.authenticationservice.mapper.ProfileMapper;
import com.rs.authenticationservice.mapper.UserMapper;
import com.rs.authenticationservice.repository.RoleRepository;
import com.rs.authenticationservice.repository.UserRepository;
import com.rs.authenticationservice.repository.httpclient.ProfileClient;
import com.rs.authenticationservice.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    ProfileMapper profileMapper;
    ProfileClient profileClient;

    @Override
    public UserResponse createUser(CreateUserRequest resquest) {
        if (userRepository.findByUsername(resquest.getUsername()).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if (userRepository.findByEmail(resquest.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        User user = userMapper.toUser(resquest);
        user.setPassword(passwordEncoder.encode(resquest.getPassword()));
        Set<Role> roles = new HashSet<>();

        roleRepository.findById(RoleEnum.USER.getName()).ifPresent(roles::add);

        user.setRoles(roles);
        userRepository.save(user);

        var profileRequest = profileMapper.toProfileCreationRequest(resquest);
        profileRequest.setUserId(user.getId());
        profileClient.createProfile(profileRequest);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(String userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        //        userMapper.updateUser(user, request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String userId) {
        return userMapper.toUserResponse(
                userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
}
