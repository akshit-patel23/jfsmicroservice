package com.itransform.order_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itransform.order_service.enums.OrderStatus;
import com.itransform.order_service.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private String orderId;
    private String userMail;
    private String washerId;
    private String carNumber;
    private String packageName;
    private List<String> addOns;
    private String notes;
    private OrderType washType;
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;
    private String location;
    private OrderStatus status;// PENDING, ACCEPTED, IN_PROCESS, COMPLETED, CANCELLED
    private double  amount;


}