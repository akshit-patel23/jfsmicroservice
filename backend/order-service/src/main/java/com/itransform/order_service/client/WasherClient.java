package com.itransform.order_service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WasherClient {

    private final WebClient.Builder webClientBuilder;

    public void setWasherAvailability(String washerId, boolean available) {
        webClientBuilder.build()
                .put()
                .uri("http://washer-service/washers/" + washerId + "/availability?available=" + available)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}