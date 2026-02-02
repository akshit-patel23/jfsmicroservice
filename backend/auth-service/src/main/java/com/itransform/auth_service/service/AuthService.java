package com.itransform.auth_service.service;

import com.itransform.auth_service.client.NotificationClient;
import com.itransform.auth_service.dto.*;
import com.itransform.auth_service.entity.User;
import com.itransform.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService   {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private NotificationClient notificationClient;

    private final Map<String, String> otpStore = new ConcurrentHashMap<>();


    @Autowired
    private WebClient.Builder webClientBuilder;

    public RegisterResponse register(RegisterRequest request){
        User user= User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole().toUpperCase())
                .build();

        userRepository.save(user);
        CompletableFuture.runAsync(()->{
            try{
                notificationClient.sendWelcomeEmail(request.getEmail());
            }
            catch (Exception ignored){}
        });
        return  new RegisterResponse(user);
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }


    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStore.put(email, otp);
        EmailNotificationDto dto = new EmailNotificationDto(email,"Password Reset Request", "Your OTP is: " + otp);
        CompletableFuture.runAsync(()->{
            try{
                notificationClient.sendOtpEmail(dto);
            }
            catch (Exception ignored){}
        });


    }


    public void resetPassword(ResetPasswordRequest request) {
        String storedOtp = otpStore.get(request.getEmail());

        if (storedOtp == null || !storedOtp.equals(request.getOtp())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        otpStore.remove(request.getEmail());
    }

}
