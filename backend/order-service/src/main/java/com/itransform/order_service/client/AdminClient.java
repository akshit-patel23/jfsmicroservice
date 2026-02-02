package com.itransform.order_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class AdminClient {

    private final WebClient webClient;



    public AdminClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://admin-service").build();
    }


    public double getpackageprice(String packageName){
        Double price= webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/admin/package/price")
                        .queryParam("packageType", packageName)
                        .build())
                .retrieve()
                .bodyToMono(Double.class)
                .block();
        if(price==null){
            throw new RuntimeException("Package price not found"+packageName);

        }
        return price;
    }

    public double getAddonPrice(String addonName) {
        Double price = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/admin/addon/price")
                        .queryParam("addonName",addonName)
                .build())
                .retrieve()
                .bodyToMono(Double.class)
                .block();

        if (price == null) {
            throw new RuntimeException("Addon price not found for: " + addonName);
        }
        return price;
    }
}
