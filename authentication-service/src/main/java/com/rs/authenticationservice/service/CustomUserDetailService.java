package com.rs.authenticationservice.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface CustomUserDetailService extends UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    default Collection<? extends GrantedAuthority> mapRolesToAuthorities(Map<String, List<String>> roles) {
        Collection<GrantedAuthority> listAuthorities = new ArrayList<>();
        if (Objects.nonNull(roles)) {
            roles.forEach((role, permissions) -> {
                listAuthorities.add(new SimpleGrantedAuthority(role));
                permissions.forEach(permission -> listAuthorities.add(new SimpleGrantedAuthority(permission)));
            });
        }

        return listAuthorities;
    }
}
