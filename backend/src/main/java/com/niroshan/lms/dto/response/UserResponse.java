package com.niroshan.lms.dto.response;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String role;
    private String profileImageUrl;
}