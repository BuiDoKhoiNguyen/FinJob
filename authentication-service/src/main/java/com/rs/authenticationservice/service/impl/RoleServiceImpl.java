package com.rs.authenticationservice.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rs.authenticationservice.dto.request.RoleRequest;
import com.rs.authenticationservice.dto.response.RoleResponse;
import com.rs.authenticationservice.entity.Role;
import com.rs.authenticationservice.mapper.RoleMapper;
import com.rs.authenticationservice.repository.PermissionRepository;
import com.rs.authenticationservice.repository.RoleRepository;
import com.rs.authenticationservice.service.RoleSevice;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleSevice {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        //        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        var role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .permissions(new HashSet<>(permissions))
                .build();
        //        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public List<RoleResponse> getAllRole() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @Override
    public void deleteRole(String role) {
        roleRepository.deleteById(role);
    }
}
