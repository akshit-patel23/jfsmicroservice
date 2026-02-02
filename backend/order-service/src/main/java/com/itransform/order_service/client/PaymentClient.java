package com.itransform.order_service.client;

import com.itransform.payment_service.dto.LinkAppOrderRequest;
import com.itransform.payment_service.dto.PaymentResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service

public class PaymentClient {

    private final WebClient webClient;

    public PaymentClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://payment-service").build();
    }


    public PaymentResponseDto createPaymentOrder(String appOrderId) {
        return webClient.post()
                .uri( "/payments/create/{appOrderId}", appOrderId)
                .retrieve()
                .bodyToMono(PaymentResponseDto.class)
                .block();
    }

    public void verifyPayment(String razorpayOrderId, String razorpayPaymentId, String signature) {
        webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/payments/verify")
                        .queryParam("razorpayOrderId", razorpayOrderId)
                        .queryParam("paymentId", razorpayPaymentId)
                        .queryParam("signature", signature)
                        .build())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void linkAppOrderId(String razorpayPaymentId, String appOrderId) {
        LinkAppOrderRequest request = new LinkAppOrderRequest();
        request.setRazorpayPaymentId(razorpayPaymentId);
        request.setAppOrderId(appOrderId);

        webClient.post()
                .uri( "/payments/link-app-order")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}