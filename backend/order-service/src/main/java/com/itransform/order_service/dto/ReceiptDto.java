package com.itransform.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptDto {
    private String afterWashImageUrl;
    private String packageName;
    private List<String> addOns;
    private double totalAmount;

//    private String addOnsSummary;
}