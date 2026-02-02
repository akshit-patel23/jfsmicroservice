package com.itransform.auth_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotificationDto {
    private String email;
    private String subject;
    private String message;
}