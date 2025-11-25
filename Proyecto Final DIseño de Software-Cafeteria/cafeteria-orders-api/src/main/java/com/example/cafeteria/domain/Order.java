package com.example.cafeteria.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("orders")
public class Order {

    @Id
    private String id;

    private String orderNumber;

    private String customerName;
    private String customerId;

    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    // total en dinero (double para que sea simple)
    private double totalAmount;

    private OrderStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    private String paymentMethod;   // ONLINE, CASH, etc.
    private String employeeId;      // id del empleado/cajero
    private String shift;           // turno: mañana / tarde / noche

    // tiempo estimado en minutos
    private Integer estimatedTimeMinutes;

    // promo aplicada si usas promociones
    private String promotionDescription;
    private Double discountAmount;      // monto del descuento aplicado
    private Double discountPercentage;  // porcentaje de descuento aplicado

    // Campos de cancelación
    private boolean cancelled;
    private String cancelledBy;
    private String cancelReason;

    @Builder.Default
    private List<OrderStatusChange> statusHistory = new ArrayList<>();
}
