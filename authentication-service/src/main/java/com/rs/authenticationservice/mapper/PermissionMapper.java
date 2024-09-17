package com.rs.authenticationservice.mapper;

import org.mapstruct.Mapper;

import com.rs.authenticationservice.dto.request.PermissionRequest;
import com.rs.authenticationservice.dto.response.PermissionResponse;
import com.rs.authenticationservice.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
