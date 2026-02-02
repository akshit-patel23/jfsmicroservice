package com.itransform.notification_service.controller;

import com.itransform.notification_service.dto.EmailNotificationDto;
import com.itransform.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

     @GetMapping("/test")
     public String test(){
         return "OK";
     }
    @PostMapping("/welcome")
    public ResponseEntity<String> sendWelcome(@RequestBody EmailNotificationDto dto) {
        notificationService.sendMail(dto);
        return ResponseEntity.ok("Welcome mail sent");
    }

    @PostMapping("/orderevent")
    public ResponseEntity<String> sendOrderNotification(@RequestBody EmailNotificationDto dto) {
        notificationService.sendMail(dto);
        return ResponseEntity.ok("Order mail sent");
    }

    @PostMapping("/payment-success")
    public ResponseEntity<String> sendPaymentMail(@RequestBody EmailNotificationDto dto) {
        notificationService.sendMail(dto); // Reuse common method
        return ResponseEntity.ok("Payment mail sent");
    }

    @PostMapping("/sendotp")
    public ResponseEntity<String> sendOtpMail(@RequestBody EmailNotificationDto dto) {
        notificationService.sendMail(dto);
        return ResponseEntity.ok("mail sent");
    }
}