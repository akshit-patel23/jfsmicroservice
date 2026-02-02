package com.itransform.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotificationDto {
    private String email;
    private String subject;
    private String message;
}