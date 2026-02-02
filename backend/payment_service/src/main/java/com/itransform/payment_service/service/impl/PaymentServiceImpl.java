package com.itransform.payment_service.service.impl;

import com.itransform.payment_service.client.OrderClient;
import com.itransform.payment_service.dto.PaymentRequestDto;
import com.itransform.payment_service.dto.PaymentResponseDto;
import com.itransform.payment_service.dto.ReceiptDto;
import com.itransform.payment_service.entity.Payment;
import com.itransform.payment_service.enums.PaymentStatus;
import com.itransform.payment_service.repository.PaymentRepository;
import com.itransform.payment_service.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderClient orderClient;

    @Value("${razorpay.key}")
    private String publicKey;

//    @Override
//    public PaymentResponseDto createPaymentOrder(String appOrderId) {
//        ReceiptDto receiptDto = orderClient.getReceiptById(appOrderId);
//
//        try {
//            long amountInPaise = (long) (receiptDto.getTotalAmount() * 100);
//
//            JSONObject options = new JSONObject();
//            options.put("amount", amountInPaise);
//            options.put("currency", "INR");
//
//            String shortId = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
//            options.put("receipt", "order_rcptid_" + shortId);
//
//            Order razorpayOrder = razorpayClient.orders.create(options);
//
//            Payment payment = Payment.builder()
//                    .orderId(razorpayOrder.get("id"))
//                    .or(appOrderId)
//                    .amountInPaise(amountInPaise)
//                    .status("CREATED")
//                    .createdAt(LocalDateTime.now())
//                    .build();
//
//            paymentRepository.save(payment);
//
//            PaymentResponseDto response = new PaymentResponseDto();
//            response.setRazorpayOrderId(razorpayOrder.get("id"));
//            response.setAmount(amountInPaise);
//            response.setCurrency("INR");
//
//            return response;
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create Razorpay order: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void verifyPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
//        Payment payment = paymentRepository.findByOrderId(razorpayOrderId);
//
//        if (payment == null) {
//            throw new RuntimeException("Payment record not found for verification");
//        }
//
//        // Optionally, implement real signature verification with HMAC (highly recommended for production)
//
//        payment.setRazorpayPaymentId(razorpayPaymentId);
//        payment.setRazorpaySignature(razorpaySignature);
//        payment.setStatus("SUCCESS");
//
//        paymentRepository.save(payment);
//    }
//
//    @Override
//    public void linkAppOrderId(String razorpayPaymentId, String appOrderId) {
//        Payment payment = paymentRepository.findByRazorpayPaymentId(razorpayPaymentId);
//
//        if (payment == null) {
//            throw new RuntimeException("Payment not found for linking App Order ID");
//        }
//
//        payment.setAppOrderId(appOrderId);
//        paymentRepository.save(payment);
//    }
//
//    @Override
//    public Payment getPaymentStatus(String paymentId) {
//        return paymentRepository.findById(paymentId)
//                .orElseThrow(() -> new RuntimeException("Payment not found"));
//    }

    @Override
    public String createPaymentLink(PaymentRequestDto requestDto) {
        try {
            JSONObject request = new JSONObject();
            request.put("amount", requestDto.getAmount()*100); // amount in paise
            request.put("currency", "INR");
            request.put("reference_id", requestDto.getOrderId());
            request.put("description", "Payment for Order ID: " + requestDto.getOrderId());

            request.put("callback_url", "http://localhost:8080/payments/payment/success");
            request.put("callback_method", "get");

            JSONObject customer = new JSONObject();
            customer.put("email", requestDto.getEmail());
            request.put("customer", customer);



            com.razorpay.PaymentLink paymentLink = razorpayClient.paymentLink.create(request);



            Payment payment = Payment.builder()
                    .orderId(requestDto.getOrderId())
                    .email(requestDto.getEmail())
                    .razorpayPaymentLinkId(paymentLink.get("id"))
                    .paymentLink(paymentLink.get("short_url"))
                    .amount(requestDto.getAmount())
                    .status(PaymentStatus.CREATED)
                    .createdAt(LocalDateTime.now())
                    .build();

            paymentRepository.save(payment);


            return paymentLink.get("short_url");

        } catch (Exception e) {
            throw new RuntimeException("Failed to create payment link: " + e.getMessage());
        }
    }

    @Override
    public List<Payment> getPaymentsByEmail(String email) {
        return paymentRepository.findByEmail(email);
    }


    @Override
    public void verifyPaymentLink(String paymentId, String linkId, String referenceId, String signature) {
        Payment payment = paymentRepository.findByRazorpayPaymentLinkId(linkId);
        if (payment == null) {
            throw new RuntimeException("Payment not found for link ID: " + linkId);
        }

        payment.setRazorpayPaymentId(paymentId);
        payment.setRazorpaySignature(signature);
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
    }


}