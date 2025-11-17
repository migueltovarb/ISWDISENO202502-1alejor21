package com.example.cafeteria.service;

import com.example.cafeteria.domain.*;
import com.example.cafeteria.repository.OrderRepository;
import com.example.cafeteria.repository.ProductRepository;
import com.example.cafeteria.repository.PromotionRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        PromotionRepository promotionRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    @Transactional
    public Order createOrder(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new ValidationException("Order must contain at least one item");
        }

        order.setOrderNumber(generateOrderNumber());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());
        calculateTotals(order);
        applyPromotions(order);
        order.setEstimatedTimeMinutes(estimateTimeMinutes(order));

        return orderRepository.save(order);
    }

    @Transactional
    public Optional<Order> updateOrderBeforePreparation(String id, Order updated) {
        Optional<Order> existingOpt = orderRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return Optional.empty();
        }
        Order existing = existingOpt.get();
        if (existing.getStatus() != OrderStatus.PENDING) {
            throw new ValidationException("Only pending orders can be modified");
        }

        existing.setCustomerName(updated.getCustomerName());
        existing.setCustomerId(updated.getCustomerId());
        existing.setItems(updated.getItems());
        existing.setPaymentMethod(updated.getPaymentMethod());
        existing.setUpdatedAt(Instant.now());
        calculateTotals(existing);
        applyPromotions(existing);
        existing.setEstimatedTimeMinutes(estimateTimeMinutes(existing));

        return Optional.of(orderRepository.save(existing));
    }

    @Transactional
    public Optional<Order> updateStatus(String id, OrderStatus status) {
        Optional<Order> existingOpt = orderRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return Optional.empty();
        }
        Order existing = existingOpt.get();
        existing.setStatus(status);
        existing.setUpdatedAt(Instant.now());
        return Optional.of(orderRepository.save(existing));
    }

    public List<Order> listAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(String id) {
        return orderRepository.findById(id);
    }

    private void calculateTotals(Order order) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ValidationException("Product not found: " + item.getProductId()));
            item.setProductName(product.getName());
            item.setCategory(product.getCategory());
            item.setUnitPrice(product.getUnitPrice());
            BigDecimal subtotal = product.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setSubtotal(subtotal);
            total = total.add(subtotal);
        }

        order.setTotalAmount(total);
    }

    private void applyPromotions(Order order) {
        List<Promotion> promotions = promotionRepository.findByActiveTrue();
        if (promotions.isEmpty()) {
            return;
        }

        Promotion promo = promotions.get(0);
        BigDecimal discount = order.getTotalAmount()
                .multiply(promo.getDiscountPercentage())
                .divide(BigDecimal.valueOf(100));
        order.setTotalAmount(order.getTotalAmount().subtract(discount));
        order.setPromotionDescription(promo.getName());
    }

    private int estimateTimeMinutes(Order order) {
        int base = 5;
        int perItem = order.getItems().stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
        int estimate = base + perItem * 2;
        return Math.min(estimate, 45);
    }

    private String generateOrderNumber() {
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "ORD-" + uuid;
    }
}
