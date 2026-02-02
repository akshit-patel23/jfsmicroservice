package com.itransform.admin_service.controller;

import com.itransform.admin_service.dto.AdminRequestDto;
import com.itransform.admin_service.dto.AdminResponseDto;
import com.itransform.admin_service.dto.*;
import com.itransform.admin_service.entity.WashPackage;
import com.itransform.admin_service.repository.AddonRepository;
import com.itransform.admin_service.repository.WashPackageRepository;
import com.itransform.admin_service.service.AdminService;
import com.itransform.admin_service.service.impl.AddonService;
import com.itransform.admin_service.entity.Addon;

import com.itransform.admin_service.service.impl.WashPackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final AddonService addonService;

    private final WashPackageService washPackageService;

    private final WashPackageRepository washPackageRepository;  // To get package details
    private final AddonRepository  addonRepository;

    @PostMapping("/complete/profile")
    public ResponseEntity<AdminResponseDto> createProfile(@RequestBody @Valid AdminRequestDto dto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(adminService.createProfile(dto, token));
    }

    @GetMapping("/profile")
    public ResponseEntity<AdminResponseDto> getProfile(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(adminService.getAdminByEmail(token));
    }

    @PatchMapping("/profile/update")
    public ResponseEntity<AdminResponseDto> updateProfile(@RequestBody @Valid AdminRequestDto dto, @RequestHeader("Authorization") String token) throws Exception {
        AdminResponseDto admin = adminService.updateProfile(dto,token);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/washers")
    public ResponseEntity<List<WasherDto>> getAllWashers() {
        return ResponseEntity.ok(adminService.getAllWashers());
    }

    @GetMapping("/getallorders")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(adminService.getAllOrders());
    }


    @PutMapping("/assign-order")
    public ResponseEntity<OrderDto> assignOrder(@RequestBody AssignOrderDto dto) {
        return ResponseEntity.ok(adminService.assignOrderToWasher(dto));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardDto>> getLeaderboard() {
        return ResponseEntity.ok(adminService.getLeaderboard());
    }

    @GetMapping("/report")
    public ResponseEntity<ReportSummaryDto> getReportSummary() {
        return ResponseEntity.ok(adminService.getReportSummary());
    }

    @PostMapping("addons/create")
    public ResponseEntity<AddonDto> createAddon(@RequestBody AddonDto addonDto) {
        return ResponseEntity.ok(addonService.createAddon(addonDto));
    }

    @GetMapping("addons/all")
    public ResponseEntity<List<AddonDto>> getAllAddons() {
        return ResponseEntity.ok(addonService.getAllAddons());
    }

    @PostMapping("addons/getByIds")
    public ResponseEntity<List<AddonDto>> getAddonsByIds(@RequestBody List<String> ids) {
        return ResponseEntity.ok(addonService.getAddonsByIds(ids));
    }

    @DeleteMapping("addons/{addonId}")
    public ResponseEntity<Void> deleteAddon(@PathVariable String addonId) {
        addonService.deleteAddon(addonId);
        return ResponseEntity.noContent().build();
    }

        // -------- Package Endpoints -------- //
            @PostMapping("/packages/create")
            public ResponseEntity<WashPackageDto> createPackage(@RequestBody WashPackageDto washPackageDto) {
                return ResponseEntity.ok(washPackageService.createPackage(washPackageDto));
            }

    @GetMapping("/packages/all")
    public ResponseEntity<List<WashPackageDto>> getAllPackages() {
        return ResponseEntity.ok(washPackageService.getAllPackages());
    }

    @GetMapping("packages/{packageId}")
    public ResponseEntity<WashPackageDto> getPackageById(@PathVariable String packageId) {
        return ResponseEntity.ok(washPackageService.getPackageById(packageId));
    }

    @DeleteMapping("packages/{packageId}")
    public ResponseEntity<Void> deletePackage(@PathVariable String packageId) {
        washPackageService.deletePackage(packageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/package/price")
    public ResponseEntity<Double> getPackagePriceByName(@RequestParam String packageType) {
        WashPackage washPackage = washPackageRepository.findByPackageType(packageType)
                .orElseThrow(() -> new RuntimeException("Package not found"));
        return ResponseEntity.ok((double) washPackage.getPrice());
    }

    @GetMapping("/addon/price")
    public ResponseEntity<Double> getAddonPriceByName(@RequestParam String addonName) {
        Addon addon = addonRepository.findByAddonName(addonName)
                .orElseThrow(() -> new RuntimeException("Addon not found"));
        return ResponseEntity.ok((double) addon.getPrice());
    }

}


