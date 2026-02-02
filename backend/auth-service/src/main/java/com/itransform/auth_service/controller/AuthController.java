package com.itransform.auth_service.controller;

import com.itransform.auth_service.dto.*;
import com.itransform.auth_service.entity.User;
import com.itransform.auth_service.repository.UserRepository;
import com.itransform.auth_service.service.AuthService;
import com.itransform.auth_service.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request){
        return  authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hello, " + userDetails.getUsername() + "(You are authenticated)";
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token){
        try{
            String username= jwtService.extractUsername(token);
            User user=userRepository.findByEmail(username)
                    .orElseThrow(()->new UsernameNotFoundException("User Not Found"));
            if(jwtService.isTokenValid(token,user)){
                return ResponseEntity.ok(new TokenValidationResponse(user.getId(), user.getName(),user.getEmail(),user.getRole()));
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            }
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.initiatePasswordReset(request.getEmail());
        return ResponseEntity.ok("OTP sent to your email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("Password reset successful");
    }

    @GetMapping("/token-info")
    public ResponseEntity<?> getTokenInfo(@RequestParam String token) {
        try {
            String username = jwtService.extractUsername(token);
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (jwtService.isTokenValid(token, user)) {
                return ResponseEntity.ok(new TokenValidationResponse(user.getId(), user.getName(),user.getEmail(),user.getRole()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

}
