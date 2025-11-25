package com.example.cafeteria.web;

import com.example.cafeteria.domain.Order;
import com.example.cafeteria.domain.OrderItem;
import com.example.cafeteria.domain.OrderStatus;
import com.example.cafeteria.service.OrderService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    // ⬇⬇⬇ Constructor explícito: aquí se inicializa orderService
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 🔎 HU007: GET con filtros opcionales
    @GetMapping
    public List<Order> findAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to
    ) {
        if (status != null && !status.isBlank()) {
            OrderStatus s = OrderStatus.valueOf(status.toUpperCase());
            return orderService.findByStatus(s);
        } else if (from != null && to != null) {
            return orderService.findByDateRange(from, to);
        } else {
            return orderService.findAll();
        }
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable String id) {
        return orderService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@Valid @RequestBody Order order) {
        return orderService.create(order);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable String id, @Valid @RequestBody Order order) {
        return orderService.update(id, order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        orderService.delete(id);
    }

    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable String id, @RequestBody UpdateStatusRequest request) {
        return orderService.updateStatus(id, request.getStatus(), request.getChangedBy());
    }

    // Cancelar pedido (solo si está PENDING)
    @PatchMapping("/{id}/cancel")
    public Order cancelOrder(@PathVariable String id, @RequestBody CancelRequest request) {
        Order order = orderService.findById(id);
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalArgumentException("Solo se pueden cancelar pedidos en estado PENDING");
        }
        // Cambiar a un estado cancelado (usaremos DELIVERED con nota)
        order.setStatus(OrderStatus.PENDING); // Mantener PENDING pero marcar como cancelado
        order.setCancelled(true);
        order.setCancelledBy(request.getCancelledBy());
        order.setCancelReason(request.getReason());
        return orderService.update(id, order);
    }

    // 🧾 HU010: comprobante del pedido
    @GetMapping("/{id}/receipt")
    public OrderReceiptResponse getReceipt(@PathVariable String id) {
        Order order = orderService.findById(id);

        OrderReceiptResponse r = new OrderReceiptResponse();
        r.setOrderId(order.getId());
        r.setOrderNumber(order.getOrderNumber());
        r.setCustomerName(order.getCustomerName());
        r.setCustomerId(order.getCustomerId());
        r.setItems(order.getItems());
        r.setTotalAmount(order.getTotalAmount());
        r.setPaymentMethod(order.getPaymentMethod());
        r.setCreatedAt(order.getCreatedAt());
        r.setStatus(order.getStatus());

        return r;
    }

    @Data
    public static class UpdateStatusRequest {
        private OrderStatus status;
        private String changedBy; // opcional
    }

    @Data
    public static class CancelRequest {
        private String cancelledBy;
        private String reason;
    }

    @Data
    public static class OrderReceiptResponse {
        private String orderId;
        private String orderNumber;
        private String customerName;
        private String customerId;
        private List<OrderItem> items;
        private double totalAmount;
        private String paymentMethod;
        private Instant createdAt;
        private OrderStatus status;
    }
}
