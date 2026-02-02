package com.itransform.order_service.client;

import com.itransform.order_service.dto.EmailNotificationDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NotificationClient {

    private final WebClient webClient;

    public NotificationClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://api-gateway").build();
    }

    public void sendOrderBookedEmail(String email) {
        EmailNotificationDto dto = new EmailNotificationDto(
                email,
                "Order Placed",
                "Your car wash order has been successfully placed!"
        );

        webClient.post()
                .uri("/notifications/orderevent")
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();


    }

}