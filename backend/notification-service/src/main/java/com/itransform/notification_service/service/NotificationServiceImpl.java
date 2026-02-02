package com.itransform.notification_service.service;

import com.itransform.notification_service.dto.EmailNotificationDto;
import com.itransform.notification_service.exception.MailSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(EmailNotificationDto dto) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(dto.getEmail());
            mail.setSubject(dto.getSubject());
            mail.setText(dto.getMessage());
            javaMailSender.send(mail);
            log.info("Email sent to {}", dto.getEmail());
        } catch (Exception e) {
            throw new MailSendException("Failed to send mail", e);
        }
    }
}