package com.itransform.admin_service.service.impl;

import com.itransform.admin_service.dto.AddonDto;
import com.itransform.admin_service.entity.Addon;
import com.itransform.admin_service.repository.AddonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddonService {

    private final AddonRepository addonRepository;

    public AddonDto createAddon(AddonDto addonDto) {
        Addon addon = Addon.builder()
                .addonName(addonDto.getAddonName())
                .price(addonDto.getPrice())
                .build();

        Addon saved = addonRepository.save(addon);

        return mapToDto(saved);
    }

    public List<AddonDto> getAllAddons() {
        return addonRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public void deleteAddon(String addonId) {
        addonRepository.deleteById(addonId);
    }

    public List<AddonDto> getAddonsByIds(List<String> ids) {
        return addonRepository.findAllById(ids).stream()
                .map(this::mapToDto)
                .toList();
    }

    private AddonDto mapToDto(Addon addon) {
        AddonDto dto = new AddonDto();
        dto.setId(addon.getId());
        dto.setAddonName(addon.getAddonName());
        dto.setPrice(addon.getPrice());
        return dto;
    }
}