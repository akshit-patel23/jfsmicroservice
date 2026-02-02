package com.itransform.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaderboardDto {
    private String username;
    private int totalWashes;
    private double waterSaved; // in gallons
}