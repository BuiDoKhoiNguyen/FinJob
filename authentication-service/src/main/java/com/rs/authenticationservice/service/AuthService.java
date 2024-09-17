package com.rs.authenticationservice.service;

import java.text.ParseException;

import com.rs.authenticationservice.dto.request.AuthRequest;
import com.rs.authenticationservice.dto.request.IntrospectRequest;
import com.rs.authenticationservice.dto.request.LogoutRequest;
import com.rs.authenticationservice.dto.request.RefreshRequest;
import com.rs.authenticationservice.dto.response.AuthResponse;
import com.rs.authenticationservice.dto.response.IntrospectResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);

    IntrospectResponse introspect(IntrospectRequest introspectRequest);

    AuthResponse refreshToken(RefreshRequest refreshToken);

    void logout(LogoutRequest logoutRequest) throws ParseException;
}
