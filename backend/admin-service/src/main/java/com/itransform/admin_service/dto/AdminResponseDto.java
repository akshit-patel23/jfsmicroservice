package com.itransform.admin_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AdminResponseDto {
    private String id;
    private String name;
    private String email;
    private String phone;
//    private String profilePicture;
    private String address;
}