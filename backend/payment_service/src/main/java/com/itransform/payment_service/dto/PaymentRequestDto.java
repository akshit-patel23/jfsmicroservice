package com.itransform.payment_service.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class PaymentRequestDto {
    private String OrderId;      // Order ID from OrderService
    private double amount;
    private String email;
}