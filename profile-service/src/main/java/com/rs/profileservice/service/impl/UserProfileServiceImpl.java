package com.rs.profileservice.service.impl;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.stereotype.Service;

import com.rs.profileservice.dto.request.CreateProfileRequest;
import com.rs.profileservice.dto.response.UserProfileResponse;
import com.rs.profileservice.entity.UserProfile;
import com.rs.profileservice.mapper.UserProfileMapper;
import com.rs.profileservice.repository.UserProfileRepository;
import com.rs.profileservice.service.UserProfileService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponse createProfile(CreateProfileRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfile = userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    @Override
    public UserProfileResponse getProfile(String id) {
        UserProfile userProfile =
                userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }
}
