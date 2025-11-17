package com.example.cafeteria.repository;

import com.example.cafeteria.domain.Order;
import com.example.cafeteria.domain.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;

import java.time.Instant;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCreatedAtBetween(Instant start, Instant end);

    @Aggregation(pipeline = {
            "{ '$unwind': '$items' }",
            "{ '$group': { '_id': '$items.productId', 'totalQuantity': { '$sum': '$items.quantity' } } }",
            "{ '$sort': { 'totalQuantity': -1 } }"
    })
    List<Object> aggregateTopProducts();
}
