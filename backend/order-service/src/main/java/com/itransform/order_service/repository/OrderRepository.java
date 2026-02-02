package com.itransform.order_service.repository;

import com.itransform.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserMail(String userId);
    List<Order> findByWasherId(String washerId);

}
