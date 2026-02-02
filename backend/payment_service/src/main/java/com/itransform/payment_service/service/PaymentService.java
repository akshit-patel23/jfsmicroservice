package com.itransform.payment_service.service;

import com.itransform.payment_service.dto.PaymentRequestDto;
import com.itransform.payment_service.dto.PaymentResponseDto;
import com.itransform.payment_service.entity.Payment;

import java.util.List;

public interface PaymentService {
//    PaymentResponseDto createPaymentOrder(String orderId);
//    void verifyPayment(String razorpayOrderId, String paymentId, String signature);
//    public void linkAppOrderId(String razorpayPaymentId, String appOrderId);
//    public Payment getPaymentStatus(String paymentId);
public void verifyPaymentLink(String paymentId, String linkId, String referenceId, String signature);
    String createPaymentLink(PaymentRequestDto requestDto);
    public List<Payment> getPaymentsByEmail(String email);
}