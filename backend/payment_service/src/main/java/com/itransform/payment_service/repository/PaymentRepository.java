package com.itransform.payment_service.repository;

import com.itransform.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    Payment findByOrderId(String orderId);

    Payment findByRazorpayPaymentLinkId(String razorpayPaymentLinkId);
    List<Payment> findByEmail(String email);
}