package com.itransform.order_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class UserClient {

    private final WebClient webClient;

    public UserClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://api-gateway").build();
    }

    public String getUserEmailById(String userId) {
        return webClient.get()
                .uri("/users/" + userId + "/email")
                .retrieve()
                .bodyToMono(String.class)
                .block();  // Block to get email synchronously
    }

    public String getUserIdByEmail(String email) {
        return webClient.get()
                .uri("http://user-service/users/id?email=" + email)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getCarIdByCarNumber(String email, String carNumber) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("http://user-service/users/car-id")
                        .queryParam("email", email)
                        .queryParam("carNumber", carNumber)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}