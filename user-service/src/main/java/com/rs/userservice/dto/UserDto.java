package com.rs.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rs.userservice.model.UserDetails;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String id;
    private String username;
    private String email;
    private UserDetails userDetails;
}
