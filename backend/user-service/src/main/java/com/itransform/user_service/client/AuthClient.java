package com.itransform.user_service.client;

import com.itransform.user_service.dto.AuthUserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthClient {

    private final WebClient webClient;

    public AuthClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://auth-service").build();
    }

    public AuthUserDto getUserFromToken(String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/auth/token-info")
                        .queryParam("token", token.replace("Bearer ",""))
                        .build())
                .retrieve()
                .bodyToMono(AuthUserDto.class)
                .block();
    }

}
