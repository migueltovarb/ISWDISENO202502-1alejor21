package com.example.cafeteria.web;

import com.example.cafeteria.domain.Order;
import com.example.cafeteria.domain.OrderStatus;
import com.example.cafeteria.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.customerName());
        order.setCustomerId(request.customerId());
        order.setPaymentMethod(request.paymentMethod());
        order.setItems(request.items());
        order.setEmployeeId(request.employeeId());
        order.setShift(request.shift());
        Order created = orderService.createOrder(order);
        return ResponseEntity.created(URI.create("/api/orders/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateBeforePreparation(@PathVariable String id,
                                                         @Valid @RequestBody CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.customerName());
        order.setCustomerId(request.customerId());
        order.setPaymentMethod(request.paymentMethod());
        order.setItems(request.items());
        order.setEmployeeId(request.employeeId());
        order.setShift(request.shift());
        return orderService.updateOrderBeforePreparation(id, order)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable String id,
                                              @Valid @RequestBody UpdateStatusRequest request) {
        return orderService.updateStatus(id, request.status())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Order> list() {
        return orderService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable String id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
