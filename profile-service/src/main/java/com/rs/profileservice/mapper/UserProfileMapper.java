package com.rs.profileservice.mapper;

import org.mapstruct.Mapper;

import com.rs.profileservice.dto.request.CreateProfileRequest;
import com.rs.profileservice.dto.response.UserProfileResponse;
import com.rs.profileservice.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(CreateProfileRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
}
