package com.itransform.api_gateway.filter;

import com.itransform.api_gateway.dto.TokenValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        ServerHttpRequest request=exchange.getRequest();

        if(request.getURI().getPath().contains("/auth")||request.getURI().getPath().equals("/payments/payment/success")){
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if(authHeader== null || !authHeader.startsWith("Bearer ")){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String token= authHeader.substring(7);

        return webClientBuilder.build()
                .get()
                .uri("lb://api-gateway/auth/validate?token=" + token)
                .retrieve()
                .bodyToMono(TokenValidationResponse.class)
                .flatMap(tokenValidationResponse -> {
                    String path=request.getURI().getPath();
                    String role=tokenValidationResponse.getRole();
                    if(path.equals("/admin/packages/all")||path.equals("/admin/addons/all")||path.equals("/washers/available")){
                        return chain.filter(exchange);
                    }
                    if(path.startsWith("/users")&& !role.equals("USER")){
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }
                    if(path.startsWith("/payments")&& !role.equals("USER")){
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }
                    if(path.startsWith("/washers")&& !role.equals("WASHER")){
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }
                    if(path.startsWith("/admin") && !role.equals("ADMIN")){
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }
                    if(path.startsWith("/orders")&& !role.equals("USER")){
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }
                    if(path.startsWith("/notifications")){
                        return chain.filter(exchange);
                    }

                    return chain.filter(exchange);
                })
                .onErrorResume(e->{
                    e.printStackTrace();
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }

    @Override
    public  int getOrder(){
        return -1;
    }


}