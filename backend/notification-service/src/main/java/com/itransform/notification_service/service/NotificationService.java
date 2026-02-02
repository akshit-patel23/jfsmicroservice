package com.itransform.notification_service.service;

import com.itransform.notification_service.dto.EmailNotificationDto;

public interface NotificationService {
    void sendMail(EmailNotificationDto dto);
}