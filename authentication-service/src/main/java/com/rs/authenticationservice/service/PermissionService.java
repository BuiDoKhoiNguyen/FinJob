package com.rs.authenticationservice.service;

import java.util.List;

import com.rs.authenticationservice.dto.request.PermissionRequest;
import com.rs.authenticationservice.dto.response.PermissionResponse;

public interface PermissionService {
    public PermissionResponse createPermission(PermissionRequest request);

    public List<PermissionResponse> getAllPermission();

    public void deletePermission(String permission);
}
