package com.itransform.order_service.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    @NotBlank(message = "Car number is required")
    private String carNumber;

    @NotBlank(message = "Package name is required")
    private String packageName;

    private List<String> addOns;

    private String notes;

    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Date must be today or in the future")
    private LocalDate date;

    @NotNull(message = "Time is required")
    private LocalTime time;

    @NotBlank(message = "Location is required")
    private String location;

    private double amount;
}
