package com.itransform.admin_service.service.impl;

import com.itransform.admin_service.client.*;
import com.itransform.admin_service.dto.*;
import com.itransform.admin_service.entity.Admin;
import com.itransform.admin_service.repository.AdminRepository;
import com.itransform.admin_service.service.AdminService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AuthClient authClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private WasherClient washerClient;
    @Autowired
    private OrderClient orderClient;

    @Override
    public AdminResponseDto createProfile(AdminRequestDto dto, String token) {
        AuthUserDto authUser = authClient.getUserFromToken(token);
        Admin admin = Admin.builder()
                .name(dto.getName())
                .email(authUser.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .build();

        admin = adminRepository.save(admin);
        return toDto(admin);
    }

    @Override
    public AdminResponseDto updateProfile(AdminRequestDto dto, String token) throws Exception {
        AuthUserDto authUser=authClient.getUserFromToken(token);
//        if(authUser==null){
//            throw new UserNotFoundException("Invalid token or unable to fetch user details");
//        }
        Admin admin=adminRepository.findByEmail(authUser.getEmail())
                .orElseThrow(()->new RuntimeException("User not found with email: "+authUser.getEmail()));
        if (dto.getName() != null && !dto.getName().isBlank()) {
            admin.setName(dto.getName());
        }
        if(dto.getPhone()!=null && !dto.getPhone().isBlank()) {
            admin.setPhone(dto.getPhone());
        }
        if(dto.getAddress()!=null && !dto.getAddress().isBlank()) {
            admin.setAddress(dto.getAddress());
        }
        Admin updatedAdmin=adminRepository.save(admin);
        return toDto(updatedAdmin);
    }


    @Override
    public AdminResponseDto getAdminByEmail(String token) {
        AuthUserDto authUser = authClient.getUserFromToken(token);

        Admin admin= adminRepository.findByEmail(authUser.getEmail())
                .orElseGet(()->{
                    Admin newAdmin= new Admin();
                    newAdmin.setName(authUser.getName());
                    newAdmin.setEmail(authUser.getEmail());
                    newAdmin.setRole(authUser.getRole());
                    return newAdmin;
                });
        Admin saved = adminRepository.save(admin);
        return toDto(saved);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userClient.getAllUsers();
    }

    @Override
    public List<WasherDto> getAllWashers() {

        return washerClient.getAllWashers();
    }

    @Override
    public List<OrderDto> getAllOrders(){

        return orderClient.getAllOrders();
    }

    @Override
    public OrderDto assignOrderToWasher(AssignOrderDto dto) {
        return orderClient.assignWasher(dto.getOrderId(), dto.getWasherId());
    }

    @Override
    public List<LeaderboardDto> getLeaderboard() {
        return orderClient.getLeaderboard();
    }

    @Override
    public ReportSummaryDto getReportSummary() {
        return orderClient.getReportSummary();
    }

    private AdminResponseDto toDto(Admin admin) {
        return AdminResponseDto.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .address(admin.getAddress())
                .build();
    }
}