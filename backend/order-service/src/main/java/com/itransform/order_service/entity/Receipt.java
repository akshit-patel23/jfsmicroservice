package com.itransform.order_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "receipts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {

    @Id
    @Column(length = 10, nullable = false, unique = true)
    private String id = RandomStringUtils.randomAlphanumeric(10);

    private String afterWashImageUrl; // After-wash car photo (stored as a URL/path)

    private String packageName;

    private List<String> addOns;

    private double totalAmount;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;
}