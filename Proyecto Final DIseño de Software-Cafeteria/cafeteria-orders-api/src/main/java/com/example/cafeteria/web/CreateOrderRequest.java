package com.example.cafeteria.web;

import com.example.cafeteria.domain.OrderItem;
import com.example.cafeteria.domain.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotEmpty String customerName,
        String customerId,
        @NotNull PaymentMethod paymentMethod,
        @NotEmpty List<OrderItem> items,
        String employeeId,
        String shift
) {
}
