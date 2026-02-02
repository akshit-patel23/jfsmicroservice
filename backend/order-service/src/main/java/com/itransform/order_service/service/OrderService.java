package com.itransform.order_service.service;

import com.itransform.order_service.dto.*;
import com.itransform.order_service.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponseDto getOrderById(String id);
    OrderResponseDto bookNow(OrderRequestDto dto,String token);
    OrderResponseDto scheduleLater(OrderRequestDto dto,String token);
    List<OrderResponseDto> getOrdersByUser(String usermail);
    List<OrderResponseDto> getOrdersByWasher(String washerId);
    void cancelOrder(String orderId);
    OrderResponseDto updateOrderStatus(String orderId, OrderStatusUpdateDto dto);
    OrderResponseDto assignWasher(String orderId, WasherAssignDto dto);
    OrderResponseDto uploadReceipt(String orderId, ReceiptDto dto);
    ReceiptDto getReceipt(String orderId);
    List<LeaderboardDto> getLeaderboard();
    List<OrderResponseDto> getAllOrders();

}