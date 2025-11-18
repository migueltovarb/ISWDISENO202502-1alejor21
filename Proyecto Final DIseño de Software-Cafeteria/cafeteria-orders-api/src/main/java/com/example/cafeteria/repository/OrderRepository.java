package com.example.cafeteria.repository;

import com.example.cafeteria.domain.Order;
import com.example.cafeteria.domain.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    // para ReportService (reportes diarios)
    List<Order> findByCreatedAtBetween(Instant start, Instant end);

    // 🔎 HU007: para filtrar por estado
    List<Order> findByStatus(OrderStatus status);
}
