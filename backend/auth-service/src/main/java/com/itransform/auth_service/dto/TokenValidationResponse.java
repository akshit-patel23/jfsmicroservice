package com.itransform.auth_service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class TokenValidationResponse {
    private String id;
    private String name;
    private String email;
    private String role;
}
