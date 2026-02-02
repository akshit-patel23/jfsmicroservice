package com.itransform.order_service.repository;

import com.itransform.order_service.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {
    Optional<Receipt> findByOrder_OrderId(String orderId);
}
