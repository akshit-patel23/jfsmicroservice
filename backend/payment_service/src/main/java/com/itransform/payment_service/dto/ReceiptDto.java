package com.itransform.payment_service.dto;

import lombok.Data;

@Data
public class ReceiptDto {
    private String afterWashImageUrl;
    private double totalAmount;
}