package com.itransform.admin_service.client;

import com.itransform.admin_service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderClient {

    private final WebClient.Builder webClientBuilder;

    public OrderDto assignWasher(String orderId, String washerId) {
        return webClientBuilder.build()
                .put()
                .uri("http://order-service/orders/" + orderId + "/assign-washer")
                .bodyValue(new AssignOrderDto(orderId, washerId))
                .retrieve()
                .bodyToMono(OrderDto.class)
                .block();
    }


    public List<LeaderboardDto> getLeaderboard() {
        return webClientBuilder.build()
                .get()
                .uri("http://order-service/orders/leaderboard")
                .retrieve()
                .bodyToFlux(LeaderboardDto.class)
                .collectList()
                .block();
    }

    public List<OrderDto> getAllOrders(){
        return webClientBuilder.build()
                .get()
                .uri("http://order-service/orders/getAllOrders")
                .retrieve()
                .bodyToFlux(OrderDto.class)
                .collectList()
                .block();
    }

    public ReportSummaryDto getReportSummary() {
        return webClientBuilder.build()
                .get()
                .uri("http://order-service/orders/report")
                .retrieve()
                .bodyToMono(ReportSummaryDto.class)
                .block();
    }


}