package com.niroshan.lms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String role;
    private final String token;
    private String profileImageUrl;
}