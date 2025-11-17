package com.example.cafeteria.repository;

import com.example.cafeteria.domain.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PromotionRepository extends MongoRepository<Promotion, String> {

    List<Promotion> findByActiveTrue();
}
