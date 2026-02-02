package com.itransform.admin_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone is required")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Phone must contain only digits")
    private String phone;

    private String email;

    @NotBlank(message = "Address is required")
    private String address;
}
