package com.rs.apigateway.repository;

import com.rs.apigateway.dto.request.IntrospectRequest;
import com.rs.apigateway.dto.response.ApiResponse;
import com.rs.apigateway.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@Repository
public interface AuthenticationClient {
    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
