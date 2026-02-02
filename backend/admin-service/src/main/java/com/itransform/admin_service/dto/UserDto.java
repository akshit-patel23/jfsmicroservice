package com.itransform.admin_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
}