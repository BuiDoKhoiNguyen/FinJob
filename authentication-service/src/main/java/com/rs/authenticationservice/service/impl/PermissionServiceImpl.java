package com.rs.authenticationservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rs.authenticationservice.dto.request.PermissionRequest;
import com.rs.authenticationservice.dto.response.PermissionResponse;
import com.rs.authenticationservice.mapper.PermissionMapper;
import com.rs.authenticationservice.repository.PermissionRepository;
import com.rs.authenticationservice.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        var permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    @Override
    public List<PermissionResponse> getAllPermission() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    @Override
    public void deletePermission(String permission) {
        permissionRepository.deleteById(permission);
    }
}
