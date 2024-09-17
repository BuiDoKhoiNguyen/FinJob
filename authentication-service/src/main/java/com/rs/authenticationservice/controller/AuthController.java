package com.rs.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rs.authenticationservice.dto.request.AuthRequest;
import com.rs.authenticationservice.dto.request.IntrospectRequest;
import com.rs.authenticationservice.dto.response.ApiResponse;
import com.rs.authenticationservice.dto.response.AuthResponse;
import com.rs.authenticationservice.dto.response.IntrospectResponse;
import com.rs.authenticationservice.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    private AuthService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthResponse>builder().result(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    //    @PostMapping("/refresh")
    //    ApiResponse<AuthResponse> authenticate(@RequestBody RefreshRequest request) {
    //        var result = authenticationService.refreshToken(request);
    //        return ApiResponse.<AuthResponse>builder().result(result).build();
    //    }
    //
    //    @PostMapping("/logout")
    //    ApiResponse<Void> logout(@RequestBody LogoutRequest request) {
    //        authenticationService.logout(request);
    //        return ApiResponse.<Void>builder().build();
    //    }
}
