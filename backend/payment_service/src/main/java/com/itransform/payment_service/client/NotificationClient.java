package com.itransform.payment_service.client;

import com.itransform.payment_service.dto.PaymentNotificationDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NotificationClient {

    private final WebClient webClient;

    public NotificationClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://api-gateway").build();
    }

    public void sendPaymentSuccessMail(PaymentNotificationDto dto) {
        webClient.post()
                .uri("/notifications/payment-success")
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block(); // blocking for simplicity
    }
}