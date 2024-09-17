package com.rs.apigateway.service;

import com.rs.apigateway.dto.request.IntrospectRequest;
import com.rs.apigateway.dto.response.ApiResponse;
import com.rs.apigateway.dto.response.IntrospectResponse;
import com.rs.apigateway.repository.AuthenticationClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    AuthenticationClient authenticationClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {
        IntrospectRequest to = IntrospectRequest.builder().token(token).build();
        return authenticationClient.introspect(to);
    }

}
