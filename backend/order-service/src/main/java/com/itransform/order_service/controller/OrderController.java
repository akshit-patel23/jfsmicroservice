package com.itransform.order_service.controller;

import com.itransform.order_service.client.PaymentClient;
import com.itransform.order_service.dto.*;
import com.itransform.order_service.entity.Order;
import com.itransform.order_service.service.OrderService;

import com.itransform.payment_service.dto.PaymentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private  OrderService orderService;

    @Autowired
    private PaymentClient paymentClient;
    @GetMapping("/{orderId}")
    public  ResponseEntity<OrderResponseDto> getOrderById(@PathVariable String orderId){
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
    @PostMapping("/book-now")
    public ResponseEntity<OrderResponseDto> bookNow(@RequestBody OrderRequestDto dto, @RequestHeader("Authorization") String token) {
        OrderResponseDto orderResponse = orderService.bookNow(dto, token);
//        PaymentResponseDto paymentResponse = paymentClient.createPaymentOrder(orderResponse.getOrderId());
//        orderResponse.setRazorpayOrderId(paymentResponse.getRazorpayOrderId());
//        orderResponse.setAmount(paymentResponse.getAmount());
        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping("/schedule")
    public ResponseEntity<OrderResponseDto> scheduleLater(@RequestBody OrderRequestDto dto, @RequestHeader("Authorization") String token) {
        OrderResponseDto orderResponse = orderService.scheduleLater(dto, token);
//        PaymentResponseDto paymentResponse = paymentClient.createPaymentOrder(orderResponse.getOrderId());
//
//
//        orderResponse.setAmount(paymentResponse.getAmount());
        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping("/payment-success")
 public ResponseEntity<String> paymentSuccess(@RequestBody PaymentVerifyRequestDto request) {

 paymentClient.verifyPayment(request.getRazorpayOrderId(), request.getRazorpayPaymentId(), request.getRazorpaySignature());

 paymentClient.linkAppOrderId(request.getRazorpayPaymentId(), request.getAppOrderId());
 OrderStatusUpdateDto orderStatusUpdate = new OrderStatusUpdateDto();
 orderStatusUpdate.setStatus("PENDING");
 orderService.updateOrderStatus(request.getAppOrderId(), orderStatusUpdate);
 return ResponseEntity.ok("Payment successful & Order confirmed");
 }


    @GetMapping("/user")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUser(@RequestParam String usermail) {
        return ResponseEntity.ok(orderService.getOrdersByUser(usermail));
    }

    @GetMapping("/washer/{washerId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByWasher(@PathVariable String washerId) {
        return ResponseEntity.ok(orderService.getOrdersByWasher(washerId));
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable String orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable String orderId,
                                                              @RequestBody OrderStatusUpdateDto dto) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, dto));
    }

    @PutMapping("/{orderId}/assign-washer")
    public ResponseEntity<OrderResponseDto> assignWasher(@PathVariable String orderId,
                                                         @RequestBody WasherAssignDto dto) {
        return ResponseEntity.ok(orderService.assignWasher(orderId, dto));
    }

    @PostMapping("/{orderId}/receipt")
    public ResponseEntity<OrderResponseDto> uploadReceipt(@PathVariable String orderId,
                                                          @RequestBody ReceiptDto dto) {
        return ResponseEntity.ok(orderService.uploadReceipt(orderId, dto));
    }

    @GetMapping("/{orderId}/receipt")
    public ResponseEntity<ReceiptDto> getReceipt(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getReceipt(orderId));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardDto>> getLeaderboard() {
        return ResponseEntity.ok(orderService.getLeaderboard());
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }


}