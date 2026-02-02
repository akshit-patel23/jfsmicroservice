package com.itransform.admin_service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private String orderId;
    private String userMail;
    private String washerId;
    private String carNumber;
    private String packageName;
    private List<String> addOns;
    private String notes;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String status;
    private double  amount;
}