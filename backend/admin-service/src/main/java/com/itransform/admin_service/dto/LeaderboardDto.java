package com.itransform.admin_service.dto;

import lombok.Data;

@Data
public class LeaderboardDto {
    private String userName;
    private int washesDone;
    private double waterSavedInLiters;
}