package com.rs.authenticationservice.service;

import java.util.List;

import com.rs.authenticationservice.dto.request.RoleRequest;
import com.rs.authenticationservice.dto.response.RoleResponse;

public interface RoleSevice {
    public RoleResponse createRole(RoleRequest request);

    public List<RoleResponse> getAllRole();

    public void deleteRole(String role);
}
