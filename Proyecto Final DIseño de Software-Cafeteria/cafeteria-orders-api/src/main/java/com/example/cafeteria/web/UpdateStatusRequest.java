package com.example.cafeteria.web;

import com.example.cafeteria.domain.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
        @NotNull OrderStatus status
) {
}
