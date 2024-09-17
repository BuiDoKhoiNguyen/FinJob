package com.rs.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rs.authenticationservice.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
