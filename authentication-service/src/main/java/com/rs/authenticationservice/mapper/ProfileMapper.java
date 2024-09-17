package com.rs.authenticationservice.mapper;

import org.mapstruct.Mapper;

import com.rs.authenticationservice.dto.request.CreateProfileRequest;
import com.rs.authenticationservice.dto.request.CreateUserRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    CreateProfileRequest toProfileCreationRequest(CreateUserRequest request);
}
