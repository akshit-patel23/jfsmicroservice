package com.itransform.admin_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportSummaryDto {
    private int totalOrders;
    private int completedOrders;
    private int cancelledOrders;
    private double totalRevenue;
}