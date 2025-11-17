package com.example.cafeteria.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String orderNumber;
    private String customerName;
    private String customerId;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private Instant createdAt;
    private Instant updatedAt;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
    private Integer estimatedTimeMinutes;
    private String employeeId;
    private String shift;
    private String promotionDescription;
}
