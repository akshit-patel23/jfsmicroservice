package com.itransform.admin_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AssignOrderDto {
    private String orderId;
    private String washerId;
}