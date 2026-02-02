package com.itransform.payment_service.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class OrderDetailsDto {
    private String orderId;
    private String userId;
    private double amount;
    private String status;
}