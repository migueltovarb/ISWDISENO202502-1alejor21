package com.example.cafeteria.repository;

import com.example.cafeteria.domain.Product;
import com.example.cafeteria.domain.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByCategory(ProductCategory category);

    List<Product> findByActiveTrue();
}
