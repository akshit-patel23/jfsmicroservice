package com.itransform.payment_service.entity;

import com.itransform.payment_service.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String razorpayPaymentId;
    private String razorpaySignature;
    private String orderId;
    private String email;
    private String razorpayPaymentLinkId;

    private String paymentLink;
    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime createdAt = LocalDateTime.now();
}