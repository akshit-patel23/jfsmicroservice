package com.itransform.admin_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WashPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String packageType;
    private int price;
    private String description;
}
