package com.itransform.auth_service.client;

import com.itransform.auth_service.dto.EmailNotificationDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NotificationClient {

    private final WebClient webClient;

    public NotificationClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://notification-service").build();
    }

    public void sendWelcomeEmail(String email) {
        EmailNotificationDto dto = new EmailNotificationDto(
                email,
                "Welcome to On Demand Carwash!",
                "Thank you for registering with On Demand Car Wash Service!"
        );

        webClient.post()
                .uri("/notifications/welcome")
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void sendOtpEmail(EmailNotificationDto dto){
        webClient.post()
                .uri("/notifications/sendotp")
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}