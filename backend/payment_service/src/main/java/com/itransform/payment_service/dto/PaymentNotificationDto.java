package com.itransform.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentNotificationDto {
    private String email;
    private String subject;
    private String message;
}
