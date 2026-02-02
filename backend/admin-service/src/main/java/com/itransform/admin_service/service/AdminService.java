package com.itransform.admin_service.service;

import com.itransform.admin_service.dto.*;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface AdminService {
    AdminResponseDto createProfile(AdminRequestDto dto, String token);
    AdminResponseDto updateProfile(AdminRequestDto dto, @RequestHeader("Authorization") String token) throws Exception;
    AdminResponseDto getAdminByEmail(String token);
    List<UserDto> getAllUsers();
    List<WasherDto> getAllWashers();
    List<OrderDto> getAllOrders();
    OrderDto assignOrderToWasher(AssignOrderDto dto);
    List<LeaderboardDto> getLeaderboard();
    ReportSummaryDto getReportSummary();


}