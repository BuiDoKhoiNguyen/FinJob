package com.rs.profileservice.service;

import com.rs.profileservice.dto.request.CreateProfileRequest;
import com.rs.profileservice.dto.response.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse createProfile(CreateProfileRequest request);

    UserProfileResponse getProfile(String id);
}
