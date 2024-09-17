package com.rs.authenticationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rs.authenticationservice.dto.request.RoleRequest;
import com.rs.authenticationservice.dto.response.RoleResponse;
import com.rs.authenticationservice.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
