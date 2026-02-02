package com.itransform.payment_service.client;

import com.itransform.payment_service.dto.OrderDetailsDto;
import com.itransform.payment_service.dto.ReceiptDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class OrderClient {

    private final WebClient webClient;

    public OrderClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://api-gateway").build();
    }

    public OrderDetailsDto getOrderById(String orderId) {
        return webClient.get()
                .uri("/orders/{orderId}", orderId)
                .retrieve()
                .bodyToMono(OrderDetailsDto.class)
                .block(); // blocking since we’re in a sync flow
    }
    public ReceiptDto getReceiptById(String orderId) {
        return webClient.get()
                .uri("/orders/{orderId}/receipt", orderId)
                .retrieve()
                .bodyToMono(ReceiptDto.class)
                .block(); // blocking since we’re in a sync flow
    }
}