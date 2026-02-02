package com.itransform.order_service.dto;

import lombok.Data;

@Data
public class PaymentVerifyRequestDto {
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
    private String appOrderId;
}
