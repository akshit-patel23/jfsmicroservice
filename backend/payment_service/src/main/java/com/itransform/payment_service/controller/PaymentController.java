package com.itransform.payment_service.controller;

import com.itransform.payment_service.dto.LinkAppOrderRequest;
import com.itransform.payment_service.dto.PaymentRequestDto;
import com.itransform.payment_service.dto.PaymentResponseDto;
import com.itransform.payment_service.entity.Payment;
import com.itransform.payment_service.enums.PaymentStatus;
import com.itransform.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.itransform.payment_service.repository.PaymentRepository;
import java.util.List;
import java.util.Map;
import org.springframework.ui.Model;
@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @GetMapping("/test")
    public String test() {
        return "tested ok";
    }

//
//    @PostMapping("/create/{appOrderId}")
//    public ResponseEntity<PaymentResponseDto> createPayment(@PathVariable String appOrderId) {
//        PaymentResponseDto response = paymentService.createPaymentOrder(appOrderId);
//        return ResponseEntity.ok(response);
//    }
//
//
//    @PostMapping("/verify")
//    public ResponseEntity<String> verifyPayment(
//            @RequestParam String razorpayOrderId,
//            @RequestParam String paymentId,
//            @RequestParam String signature
//    ) {
//        paymentService.verifyPayment(razorpayOrderId, paymentId, signature);
//        return ResponseEntity.ok("Payment verified successfully");
//    }
//
//
//    @PostMapping("/link-app-order")
//    public ResponseEntity<String> linkAppOrderId(@RequestBody LinkAppOrderRequest request) {
//        paymentService.linkAppOrderId(request.getRazorpayPaymentId(), request.getAppOrderId());
//        return ResponseEntity.ok("App Order ID linked successfully");
//    }
//
//
//    @GetMapping("/status/{paymentId}")
//    public ResponseEntity<?> getPaymentStatus(@PathVariable String paymentId) {
//        return ResponseEntity.ok(paymentService.getPaymentStatus(paymentId));
//    }

    @PostMapping("/create-payment-link")
    public ResponseEntity<Map<String, String>> createPaymentLink(@RequestBody PaymentRequestDto requestDto) {
        String paymentLink = paymentService.createPaymentLink(requestDto);
        return ResponseEntity.ok(Map.of("paymentLink", paymentLink));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Payment>> getPaymentsByUserMail(@RequestParam String usermail) {
        List<Payment> paymentsbymail = paymentService.getPaymentsByEmail(usermail);
        return ResponseEntity.ok(paymentsbymail);
    }
    @GetMapping("/payment/success")
    public String handlePaymentSuccess(
            @RequestParam("razorpay_payment_id") String paymentId,
            @RequestParam("razorpay_payment_link_id") String linkId,
            @RequestParam("razorpay_payment_link_reference_id") String referenceId,
            @RequestParam("razorpay_signature") String signature,
            Model model) {

        paymentService.verifyPaymentLink(paymentId, linkId, referenceId, signature);
        model.addAttribute("paymentId", paymentId);
        return "payment-success"; // this should match your success page name
    }
    @GetMapping("/testui")
    public String testthymeleaf() {
        return "ok";
    }

}