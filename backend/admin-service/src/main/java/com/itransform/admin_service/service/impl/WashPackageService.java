package com.itransform.admin_service.service.impl;

import com.itransform.admin_service.dto.WashPackageDto;
import com.itransform.admin_service.entity.WashPackage;
import com.itransform.admin_service.repository.WashPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class WashPackageService {

    private final WashPackageRepository washPackageRepository;

    public WashPackageDto createPackage(WashPackageDto washPackageDto) {
        WashPackage washPackage = WashPackage.builder()
                .packageType(washPackageDto.getPackageType())
                .price(washPackageDto.getPrice())
                .build();

        WashPackage saved = washPackageRepository.save(washPackage);

        return mapToDto(saved);
    }

    public List<WashPackageDto> getAllPackages() {
        List<WashPackage> packages = washPackageRepository.findAll();
        return packages.stream()
                .map(this::mapToDto)
                .toList();
    }

    public void deletePackage(String packageId) {
        washPackageRepository.deleteById(packageId);
    }

    public WashPackageDto getPackageById(String packageId) {
        WashPackage washPackage = washPackageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("WashPackage not found"));

        return mapToDto(washPackage);
    }

    private WashPackageDto mapToDto(WashPackage washPackage) {
        WashPackageDto dto = new WashPackageDto();
        dto.setId(washPackage.getId());
        dto.setPackageType(washPackage.getPackageType());
        dto.setPrice(washPackage.getPrice());
        return dto;
    }
}