package com.itransform.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateDto {
    private String status; // ACCEPTED, IN_PROCESS, COMPLETED, CANCELLED
}