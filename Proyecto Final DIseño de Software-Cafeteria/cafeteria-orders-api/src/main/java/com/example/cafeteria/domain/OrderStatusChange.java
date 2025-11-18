package com.example.cafeteria.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusChange {

    private OrderStatus fromStatus;
    private OrderStatus toStatus;
    private Instant changedAt;
    private String changedBy; // opcional: id/username de empleado
}
