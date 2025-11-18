package com.example.cafeteria.service;

import com.example.cafeteria.domain.Order;
import com.example.cafeteria.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final OrderRepository orderRepository;

    public ReportService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Map<String, Object> dailyReport(LocalDate date) {
        Instant start = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant end = date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

        List<Order> orders = orderRepository.findByCreatedAtBetween(start, end);

        int totalOrders = orders.size();

        // totalAmount es double → convertimos a BigDecimal
        BigDecimal totalIncome = orders.stream()
                .map(order -> BigDecimal.valueOf(order.getTotalAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("totalOrders", totalOrders);
        result.put("totalIncome", totalIncome);
        return result;
    }
}
