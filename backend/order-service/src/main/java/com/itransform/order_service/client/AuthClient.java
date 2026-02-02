package com.itransform.order_service.client;

import com.itransform.order_service.dto.AuthUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthClient {

    private final  WebClient webClient;

    public AuthClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://api-gateway").build();
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
