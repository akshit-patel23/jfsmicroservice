package com.itransform.admin_service.client;

import com.itransform.admin_service.dto.WasherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WasherClient {

    private final WebClient.Builder webClientBuilder;

    public List<WasherDto> getAllWashers() {
        return webClientBuilder.build()
                .get()
                .uri("http://washer-service/washers")
                .retrieve()
                .bodyToFlux(WasherDto.class)
                .collectList()
                .block();
    }
}