package com.itransform.order_service.entity;

import com.itransform.order_service.dto.OrderRequestDto;
import com.itransform.order_service.enums.OrderStatus;
import com.itransform.order_service.enums.OrderType;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {


    @Id
    @Column(length = 10, nullable = false, unique = true)
    private String orderId = RandomStringUtils.randomAlphanumeric(10);

    private String userMail;

    private String carNumber;

    private String washerId;

    private String packageName;

    private List<String> addOns;

    private String notes;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private OrderType washtype;

    private LocalDate date;

    private LocalTime time;

    private double totalAmount;

    private String location;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Receipt receipt;

    public Order(OrderRequestDto dto, OrderType washType, OrderStatus status,String userMail) {
        this.userMail=userMail;
        this.carNumber= dto.getCarNumber();
        this.packageName = dto.getPackageName();
        this.addOns = dto.getAddOns();
        this.notes = dto.getNotes();
        this.date = dto.getDate();
        this.time = dto.getTime();
        this.location = dto.getLocation();
        this.washtype = washType;
        this.status = status;
        this.totalAmount=dto.getAmount();
    }

}