package com.rs.authenticationservice.service.impl;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rs.authenticationservice.dto.request.AuthRequest;
import com.rs.authenticationservice.dto.request.IntrospectRequest;
import com.rs.authenticationservice.dto.request.LogoutRequest;
import com.rs.authenticationservice.dto.request.RefreshRequest;
import com.rs.authenticationservice.dto.response.AuthResponse;
import com.rs.authenticationservice.dto.response.IntrospectResponse;
import com.rs.authenticationservice.entity.InvalidatedToken;
import com.rs.authenticationservice.exception.AppException;
import com.rs.authenticationservice.exception.ErrorCode;
import com.rs.authenticationservice.repository.InvalidatedTokenRepository;
import com.rs.authenticationservice.repository.UserRepository;
import com.rs.authenticationservice.service.AuthService;
import com.rs.authenticationservice.service.jwt.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    CustomUserDetailsService customUserDetailsService;
    JwtUtil jwtUtil;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        boolean isValid = true;

        try {
            jwtUtil.verifyToken(token, false);
        } catch (Exception e) {
            isValid = false;
        }
        return IntrospectResponse.builder().valid(isValid).build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());

        String token = jwtUtil.generateToken(userDetails);

        return AuthResponse.builder()
                .jwt(token)
                .expiryTime(jwtUtil.extractExpiration(token))
                .build();
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        try {
            Claims token = jwtUtil.verifyToken(logoutRequest.getToken(), true);
            String jti = token.getId();
            Date expiryTime = token.getExpiration();

            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jti).expiryTime(expiryTime).build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token already expired");
        }
    }

    @Override
    public AuthResponse refreshToken(RefreshRequest refreshToken) {
        return jwtUtil.refreshToken(refreshToken);
    }
}
