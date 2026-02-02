package com.itransform.payment_service.dto;

import lombok.Data;

@Data
public class LinkAppOrderRequest {
    private String razorpayPaymentId;
    private String appOrderId;
}
