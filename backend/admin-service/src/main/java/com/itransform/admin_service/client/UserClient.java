package com.itransform.admin_service.client;

import com.itransform.admin_service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserClient {

    private final WebClient.Builder webClientBuilder;

    public List<UserDto> getAllUsers() {
        return webClientBuilder.build()
                .get()
                .uri("http://user-service/users/allusers")
                .retrieve()
                .bodyToFlux(UserDto.class)
                .collectList()
                .block();
    }
}