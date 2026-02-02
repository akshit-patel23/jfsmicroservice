package com.itransform.payment_service.dto;

import lombok.Data;

@Data
public class PaymentResponseDto {
    private String razorpayOrderId;
    private double amount;
    private String currency;
}