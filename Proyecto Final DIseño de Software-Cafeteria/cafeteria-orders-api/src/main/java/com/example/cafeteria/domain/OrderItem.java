package com.example.cafeteria.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {

    private String productId;
    private String productName;
    private ProductCategory category;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
