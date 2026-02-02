package com.itransform.order_service.service.impl;

import com.itransform.order_service.client.*;
import com.itransform.order_service.dto.*;
import com.itransform.order_service.entity.Order;
import com.itransform.order_service.entity.Receipt;
import com.itransform.order_service.enums.OrderStatus;
import com.itransform.order_service.enums.OrderType;
import com.itransform.order_service.repository.OrderRepository;
import com.itransform.order_service.repository.ReceiptRepository;
import com.itransform.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.BootstrapContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private UserClient userClient;
    @Autowired
    private AuthClient authClient;

    @Autowired
    private WasherClient washerClient;
    @Autowired
    private AdminClient adminClient;


    @Override
    public OrderResponseDto getOrderById(String id){
        return toDto(Objects.requireNonNull(orderRepository.findById(id).orElse(null)));
    }

    @Override
    public OrderResponseDto bookNow(OrderRequestDto dto,String token) {
        AuthUserDto authUser=authClient.getUserFromToken(token);
        if(authUser==null){
            throw new RuntimeException("Invalid token or unable to fetch user details");
        }
        String email=authUser.getEmail();
        dto.setTime(LocalTime.now());
        dto.setDate(LocalDate.now());


        Double totalAmount = adminClient.getpackageprice(dto.getPackageName());

        List<String> resolvedAddonNames = new ArrayList<>();


        if (dto.getAddOns() != null) {
            for (String addonName : dto.getAddOns()) {
                Double addonPrice = adminClient.getAddonPrice(addonName);
                totalAmount += (addonPrice != null ? addonPrice : 0);
                resolvedAddonNames.add(addonName);
            }
        }
        dto.setAmount(totalAmount);
        Order order = new Order(dto, OrderType.NOW, OrderStatus.PENDING,email);
        CompletableFuture.runAsync(()->{
            try{
                notificationClient.sendOrderBookedEmail(email);
            }
            catch (Exception ignored){}
        });
        return toDto(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto scheduleLater(OrderRequestDto dto,String token) {
        AuthUserDto authUser=authClient.getUserFromToken(token);
        if(authUser==null){
            throw new RuntimeException("Invalid token or unable to fetch user details");
        }
        String email=authUser.getEmail();
        Order order = new Order(dto, OrderType.SCHEDULED, OrderStatus.PENDING,email);
        notificationClient.sendOrderBookedEmail(email);
        return toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(String usermail) {
        List<Order> orders = orderRepository.findByUserMail(usermail);
        return orders.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDto> getOrdersByWasher(String washerId) {
        List<Order> orders = orderRepository.findByWasherId(washerId);
        return orders.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public OrderResponseDto updateOrderStatus(String orderId, OrderStatusUpdateDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.valueOf(dto.getStatus().toUpperCase()));
        return toDto(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto assignWasher(String orderId, WasherAssignDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setWasherId(dto.getWasherId());
        order.setStatus(OrderStatus.ASSIGNED);
        washerClient.setWasherAvailability(dto.getWasherId(), false);
        return toDto(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto uploadReceipt(String orderId, ReceiptDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Receipt receipt = new Receipt();
        receipt.setOrder(order);
        receipt.setAfterWashImageUrl(dto.getAfterWashImageUrl());
        receipt.setPackageName(dto.getPackageName());
        receipt.setAddOns(dto.getAddOns());
        receipt.setTotalAmount(dto.getTotalAmount());
        receiptRepository.save(receipt);
        order.setReceipt(receipt);
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        return toDto(order);
    }

    @Override
    public ReceiptDto getReceipt(String orderId) {
        Receipt receipt = receiptRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Receipt not found"));

        return ReceiptDto.builder()
                .afterWashImageUrl(receipt.getAfterWashImageUrl())
                .packageName(receipt.getPackageName())
                .addOns(receipt.getAddOns())
                .totalAmount(receipt.getTotalAmount())
                .build();
    }

    @Override
    public List<LeaderboardDto> getLeaderboard() {
//        return orderRepository.getWaterSavedLeaderboard();
        return  null;
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    private OrderResponseDto toDto(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .userMail(order.getUserMail())
                .washerId(order.getWasherId())
                .carNumber(order.getCarNumber())
                .packageName(order.getPackageName())
                .addOns(order.getAddOns())
                .notes(order.getNotes())
                .status(order.getStatus())
                .washType(order.getWashtype())
                .date(order.getDate())
                .time(order.getTime())
                .location(order.getLocation())
                .amount(order.getTotalAmount())
                .build();
    }



}